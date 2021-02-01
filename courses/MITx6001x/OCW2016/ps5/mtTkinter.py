'''Thread-safe version of Tkinter.

Copyright (c) 2009, Allen B. Taylor

This module is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser Public License for more details.

You should have received a copy of the GNU Lesser Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Usage:

    import mtTkinter as Tkinter
    # Use "Tkinter." as usual.

or

    from mtTkinter import *
    # Use Tkinter module definitions as usual.

This module modifies the original Tkinter module in memory, making all
functionality thread-safe. It does this by wrapping the Tk class' tk
instance with an object that diverts calls through an event queue when
the call is issued from a thread other than the thread in which the Tk
instance was created. The events are processed in the creation thread
via an 'after' event.

The modified Tk class accepts two additional keyword parameters on its
__init__ method:
    mtDebug:
        0 = No debug output (default)
        1 = Minimal debug output
        ...
        9 = Full debug output
    mtCheckPeriod:
        Amount of time in milliseconds (default 100) between checks for
        out-of-thread events when things are otherwise idle. Decreasing
        this value can improve GUI responsiveness, but at the expense of
        consuming more CPU cycles.

Note that, because it modifies the original Tkinter module (in memory),
other modules that use Tkinter (e.g., Pmw) reap the benefits automagically
as long as mtTkinter is imported at some point before extra threads are
created.

Author: Allen B. Taylor, a.b.taylor@gmail.com
'''

from tkinter import *
import threading
import queue

class _Tk(object):
    """
    Wrapper for underlying attribute tk of class Tk.
    """

    def __init__(self, tk, mtDebug = 0, mtCheckPeriod = 10):
        self._tk = tk

        # Create the incoming event queue.
        self._eventQueue = queue.Queue(1)

        # Identify the thread from which this object is being created so we can
        # tell later whether an event is coming from another thread.
        self._creationThread = threading.currentThread()

        # Store remaining values.
        self._debug = mtDebug
        self._checkPeriod = mtCheckPeriod

    def __getattr__(self, name):
        # Divert attribute accesses to a wrapper around the underlying tk
        # object.
        return _TkAttr(self, getattr(self._tk, name))

class _TkAttr(object):
    """
    Thread-safe callable attribute wrapper.
    """

    def __init__(self, tk, attr):
        self._tk = tk
        self._attr = attr

    def __call__(self, *args, **kwargs):
        """
        Thread-safe method invocation.
        Diverts out-of-thread calls through the event queue.
        Forwards all other method calls to the underlying tk object directly.
        """

        # Check if we're in the creation thread.
        if threading.currentThread() == self._tk._creationThread:
            # We're in the creation thread; just call the event directly.
            if self._tk._debug >= 8 or \
               self._tk._debug >= 3 and self._attr.__name__ == 'call' and \
               len(args) >= 1 and args[0] == 'after':
                print( 'Calling event directly:', \
                    self._attr.__name__, args, kwargs)
            return self._attr(*args, **kwargs)
        else:
            # We're in a different thread than the creation thread; enqueue
            # the event, and then wait for the response.
            responseQueue = queue.Queue(1)
            if self._tk._debug >= 1:
                print( 'Marshalling event:', self._attr.__name__, args, kwargs)
            self._tk._eventQueue.put((self._attr, args, kwargs, responseQueue))
            isException, response = responseQueue.get()

            # Handle the response, whether it's a normal return value or
            # an exception.
            if isException:
                exType, exValue, exTb = response
                raise exType#, exValue, exTb
            else:
                return response

# Define a hook for class Tk's __init__ method.
def _Tk__init__(self, *args, **kwargs):
    # We support some new keyword arguments that the original __init__ method
    # doesn't expect, so separate those out before doing anything else.
    new_kwnames = ('mtCheckPeriod', 'mtDebug')
    new_kwargs = {}
    for name, value in kwargs.items():
        if name in new_kwnames:
            new_kwargs[name] = value
            del kwargs[name]

    # Call the original __init__ method, creating the internal tk member.
    self.__original__init__mtTkinter(*args, **kwargs)

    # Replace the internal tk member with a wrapper that handles calls from
    # other threads.
    self.tk = _Tk(self.tk, **new_kwargs)

    # Set up the first event to check for out-of-thread events.
    self.after_idle(_CheckEvents, self)

# Replace Tk's original __init__ with the hook.
Tk.__original__init__mtTkinter = Tk.__init__
Tk.__init__ = _Tk__init__

def _CheckEvents(tk):
    "Event checker event."

    used = False
    try:
        # Process all enqueued events, then exit.
        while True:
            try:
                # Get an event request from the queue.
                method, args, kwargs, responseQueue = \
                    tk.tk._eventQueue.get_nowait()
            except:
                # No more events to process.
                break
            else:
                # Call the event with the given arguments, and then return
                # the result back to the caller via the response queue.
                used = True
                if tk.tk._debug >= 2:
                    print( 'Calling event from main thread:', \
                        method.__name__, args, kwargs)
                try:
                    responseQueue.put((False, method(*args, **kwargs)))
                except (SystemExit, ex):
                    raise (SystemExit, ex)
                except (Exception, ex):
                    # Calling the event caused an exception; return the
                    # exception back to the caller so that it can be raised
                    # in the caller's thread.
                    from sys import exc_info
                    exType, exValue, exTb = exc_info()
                    responseQueue.put((True, (exType, exValue, exTb)))
    finally:
        # Schedule to check again. If we just processed an event, check
        # immediately; if we didn't, check later.
        if used:
            tk.after_idle(_CheckEvents, tk)
        else:
            tk.after(tk.tk._checkPeriod, _CheckEvents, tk)

# Test thread entry point.
def _testThread(root):
    text = "This is Tcl/Tk version %s" % TclVersion
    if TclVersion >= 8.1:
        try:
            text = text + unicode("\nThis should be a cedilla: \347",
                                  "iso-8859-1")
        except NameError:
            pass # no unicode support
    try:
        if root.globalgetvar('tcl_platform(threaded)'):
            text = text + "\nTcl is built with thread support"
        else:
            raise RuntimeError
    except:
        text = text + "\nTcl is NOT built with thread support"
    text = text + "\nmtTkinter works with or without Tcl thread support"
    label = Label(root, text=text)
    label.pack()
    button = Button(root, text="Click me!",
              command=lambda root=root: root.button.configure(
                  text="[%s]" % root.button['text']))
    button.pack()
    root.button = button
    quit = Button(root, text="QUIT", command=root.destroy)
    quit.pack()
    # The following three commands are needed so the window pops
    # up on top on Windows...
    root.iconify()
    root.update()
    root.deiconify()
    # Simulate button presses...
    button.invoke()
    root.after(1000, _pressOk, root, button)

# Test button continuous press event.
def _pressOk(root, button):
    button.invoke()
    try:
        root.after(1000, _pressOk, root, button)
    except:
        pass # Likely we're exiting

# Test. Mostly borrowed from the Tkinter module, but the important bits moved
# into a separate thread.
if __name__ == '__main__':
    import threading
    root = Tk(mtDebug = 1)
    thread = threading.Thread(target = _testThread, args=(root,))
    thread.start()
    root.mainloop()
    thread.join()

(require 2htdp/image)
(require 2htdp/universe)

;; rolling-lambda-starter.rkt

;
; PROBLEM:
;
; Design a world program as follows:
;
; The world starts off with a lambda on the left hand side of the screen. As
; time passes, the lambda will roll towards the right hand side of the screen.
; Clicking the mouse changes the direction the lambda is rolling (ie from
; left -> right to right -> left). If the lambda hits the side of the window
; it should also change direction.
;
; Starting display (rolling to the right):
;
; .
;
; After a few seconds (rolling to the right):
;       .
; After a few more seconds (rolling to the right):
;                .
; A few seconds after clicking the mouse (rolling to the left):
;
;      .
;
; NOTE 1: Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;
; NOTE 2: DO THIS PROBLEM IN STAGES.
;
; FIRST
;
; Just make the lambda slide back and forth across the screen without rolling.
;
; SECOND
;
; Make the lambda spin as it slides, but don't worry about making the spinning
; be just exactly right to make it look like its rolling. Just have it
; spinning and sliding back and forth across the screen.
;
; FINALLY
;
; Work out the math you need to in order to make the lambda look like it is
; actually rolling.  Remember that the circumference of a circle is 2*pi*radius,
; so that for each degree of rotation the circle needs to move:
;
;    2*pi*radius
;    -----------
;        360
;
; Also note that the rotate function requires an angle in degrees as its
; first argument. [By that it means Number[0, 360). As time goes by the lambda
; may end up spinning more than once, for example, you may get to a point
; where it has spun 362 degrees, which rotate won't accept. One solution to
; that is to  use the modulo function as follows:
;
; (rotate (modulo ... 360) LAMBDA)
;
; where ... can be an expression that produces any positive number of degrees
; and remainder will produce a number in [0, 360).
;
; Remember that you can lookup the documentation of modulo if you need to know
; more about it.
;

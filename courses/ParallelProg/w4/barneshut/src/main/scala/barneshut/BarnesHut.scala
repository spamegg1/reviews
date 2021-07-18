package barneshut

import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.event.*
import scala.compiletime.uninitialized
import scala.collection.parallel.*
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

object BarnesHut:

  val model = SimulationModel()

  var simulator: Simulator = uninitialized

  def initialize(parallelismLevel: Int, pattern: String, nbodies: Int): Unit =
    model.initialize(parallelismLevel, pattern, nbodies)
    model.timeStats.clear()
    simulator = Simulator(model.taskSupport, model.timeStats)

  class BarnesHutFrame extends JFrame("Barnes-Hut"):
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setSize(1024, 600)
    setLayout(BorderLayout())

    val rightpanel = JPanel()
    rightpanel.setBorder(BorderFactory.createEtchedBorder(border.EtchedBorder.LOWERED))
    rightpanel.setLayout(BorderLayout())
    add(rightpanel, BorderLayout.EAST)

    val controls = JPanel()
    controls.setLayout(GridLayout(0, 2))
    rightpanel.add(controls, BorderLayout.NORTH)

    val parallelismLabel = JLabel("Parallelism")
    controls.add(parallelismLabel)

    val items = (1 to Runtime.getRuntime.availableProcessors).map(_.toString).toArray
    val parcombo = JComboBox[String](items)
    parcombo.setSelectedIndex(items.length - 1)
    parcombo.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) = {
        initialize(getParallelism, "two-galaxies", getTotalBodies)
        canvas.repaint()
      }
    })
    controls.add(parcombo)

    val bodiesLabel = JLabel("Total bodies")
    controls.add(bodiesLabel)

    val bodiesSpinner = JSpinner(SpinnerNumberModel(25000, 32, 1000000, 1000))
    bodiesSpinner.addChangeListener(new ChangeListener {
      def stateChanged(e: ChangeEvent) = {
        if frame != null then {
          initialize(getParallelism, "two-galaxies", getTotalBodies)
          canvas.repaint()
        }
      }
    })
    controls.add(bodiesSpinner)

    val stepbutton = JButton("Step")
    stepbutton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        stepThroughSimulation()
      }
    })
    controls.add(stepbutton)

    val startButton = JToggleButton("Start/Pause")
    val startTimer = javax.swing.Timer(0, new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        stepThroughSimulation()
      }
    })
    startButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        if startButton.isSelected then startTimer.start()
        else startTimer.stop()
      }
    })
    controls.add(startButton)

    val quadcheckbox = JToggleButton("Show quad")
    quadcheckbox.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        model.shouldRenderQuad = quadcheckbox.isSelected
        repaint()
      }
    })
    controls.add(quadcheckbox)

    val clearButton = JButton("Restart")
    clearButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        initialize(getParallelism, "two-galaxies", getTotalBodies)
      }
    })
    controls.add(clearButton)

    val info = JTextArea("   ")
    info.setBorder(BorderFactory.createLoweredBevelBorder)
    rightpanel.add(info, BorderLayout.SOUTH)

    val canvas = SimulationCanvas(model)
    add(canvas, BorderLayout.CENTER)
    setVisible(true)

    def updateInformationBox(): Unit =
      val text = model.timeStats.toString
      frame.info.setText("--- Statistics: ---\n" + text)

    def stepThroughSimulation(): Unit =
      SwingUtilities.invokeLater(new Runnable {
        def run() = {
          val (bodies, quad) = simulator.step(model.bodies)
          model.bodies = bodies
          model.quad = quad
          updateInformationBox()
          repaint()
        }
      })

    def getParallelism =
      val selidx = parcombo.getSelectedIndex
      parcombo.getItemAt(selidx).toInt

    def getTotalBodies = bodiesSpinner.getValue.asInstanceOf[Int]

    initialize(getParallelism, "two-galaxies", getTotalBodies)

  try
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
  catch
    case _: Exception => println("Cannot set look and feel, using the default one.")

  val frame = BarnesHutFrame()

  def main(args: Array[String]): Unit =
    frame.repaint()


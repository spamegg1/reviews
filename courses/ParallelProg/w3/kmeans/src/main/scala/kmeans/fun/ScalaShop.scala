package kmeans
package fun

import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.event.*
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import org.scalameter.*

object ScalaShop:

  class ScalaShopFrame extends JFrame("ScalaShop\u2122"):
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setSize(800, 500)
    setLayout(BorderLayout())

    val rightpanel = JPanel()
    rightpanel.setBorder(BorderFactory.createEtchedBorder(border.EtchedBorder.LOWERED))
    rightpanel.setLayout(BorderLayout())
    add(rightpanel, BorderLayout.EAST)

    val allControls = JPanel()
    allControls.setLayout(BoxLayout(allControls, BoxLayout.Y_AXIS))
    rightpanel.add(allControls, BorderLayout.NORTH)

    // Color count selection
    val colorControls = JPanel()
    colorControls.setLayout(GridLayout(0, 2))
    allControls.add(colorControls)

    val colorCountLabel = JLabel("Colors")
    colorControls.add(colorCountLabel)

    val colorCountSpinner = JSpinner(SpinnerNumberModel(32, 16, 512, 16))
    colorControls.add(colorCountSpinner)

    // Initial selection
    val initSelectionControls = JPanel()
    initSelectionControls.setLayout(GridLayout(0, 1))
    allControls.add(initSelectionControls)

    val initialSelectionGroup = ButtonGroup()

    val initSelectionLabel = JLabel("Initial Color Selection:")
    initSelectionControls.add(initSelectionLabel)

    val uniformSamplingButton = JRadioButton("Uniform Sampling")
    uniformSamplingButton.setSelected(true);
    initSelectionControls.add(uniformSamplingButton)

    val randomSamplingButton = JRadioButton("Random Sampling")
    initSelectionControls.add(randomSamplingButton)

    val uniformChoiceButton = JRadioButton("Uniform Choice")
    initSelectionControls.add(uniformChoiceButton)

    initialSelectionGroup.add(randomSamplingButton)
    initialSelectionGroup.add(uniformSamplingButton)
    initialSelectionGroup.add(uniformChoiceButton)

    // Initial means selection
    val convergenceControls = JPanel()
    convergenceControls.setLayout(BoxLayout(convergenceControls, BoxLayout.Y_AXIS))
    allControls.add(convergenceControls)

    val convergenceGroup = ButtonGroup()

    val convergenceLabel = JLabel("Convergence criteria:")
    initSelectionControls.add(convergenceLabel)

    val criteriaControls = JPanel()
    criteriaControls.setLayout(GridLayout(0, 2))
    convergenceControls.add(criteriaControls)

    val stepConvergenceButton = JRadioButton("Steps")
    criteriaControls.add(stepConvergenceButton)

    val stepCountSpinner = JSpinner(SpinnerNumberModel(5, 1, 50, 1))
    criteriaControls.add(stepCountSpinner)

    val etaConvergenceButton = JRadioButton("Eta")
    etaConvergenceButton.setSelected(true);
    criteriaControls.add(etaConvergenceButton)

    val etaCountSpinner = JSpinner(SpinnerNumberModel(0.001, 0.00001, 0.01, 0.00001))
    criteriaControls.add(etaCountSpinner)

    val snrConvergenceButton = JRadioButton("Sound-to-noise")
    criteriaControls.add(snrConvergenceButton)

    val snrCountSpinner = JSpinner(SpinnerNumberModel(40, 10, 80, 1))
    criteriaControls.add(snrCountSpinner)

    convergenceGroup.add(stepConvergenceButton)
    convergenceGroup.add(etaConvergenceButton)
    convergenceGroup.add(snrConvergenceButton)

    // Action Buttons
    val actionControls = JPanel()
    actionControls.setLayout(GridLayout(0, 2))
    allControls.add(actionControls)

    val stepbutton = JButton("Apply filter")
    stepbutton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        var status = ""
        val time = measure {
          status = canvas.applyIndexedColors(getColorCount, getInitialSelectionStrategy, getConvergenceStragegy)
        }
        updateInformationBox(status, time.value)
      }
    })
    actionControls.add(stepbutton)

    val clearButton = JButton("Reload")
    clearButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        canvas.reload()
      }
    })
    actionControls.add(clearButton)

    val info = JTextArea("              ")
    info.setBorder(BorderFactory.createLoweredBevelBorder)
    rightpanel.add(info, BorderLayout.SOUTH)

    val mainMenuBar = JMenuBar()

    val fileMenu = JMenu("File")
    val openMenuItem = JMenuItem("Open...")
    openMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        val fc = JFileChooser()
        if fc.showOpenDialog(ScalaShopFrame.this) == JFileChooser.APPROVE_OPTION then {
          canvas.loadFile(fc.getSelectedFile.getPath)
        }
      }
    })
    fileMenu.add(openMenuItem)
    val saveMenuItem = JMenuItem("Save...")
    saveMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        val fc = JFileChooser("epfl-view.png")
        if fc.showSaveDialog(ScalaShopFrame.this) == JFileChooser.APPROVE_OPTION then {
          canvas.saveFile(fc.getSelectedFile.getPath)
        }
      }
    })
    fileMenu.add(saveMenuItem)
    val exitMenuItem = JMenuItem("Exit")
    exitMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        sys.exit(0)
      }
    })
    fileMenu.add(exitMenuItem)

    mainMenuBar.add(fileMenu)

    val helpMenu = JMenu("Help")
    val aboutMenuItem = JMenuItem("About")
    aboutMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent): Unit = {
        JOptionPane.showMessageDialog(null, "ScalaShop, the ultimate image manipulation tool\nBrought to you by EPFL, 2015")
      }
    })
    helpMenu.add(aboutMenuItem)

    mainMenuBar.add(helpMenu)

    setJMenuBar(mainMenuBar)

    val canvas = PhotoCanvas()

    val scrollPane = JScrollPane(canvas)

    add(scrollPane, BorderLayout.CENTER)
    setVisible(true)

    def updateInformationBox(status: String, time: Double): Unit =
      info.setText(s"$status\nTime: ${time.toInt} ms.")

    def getColorCount: Int =
      colorCountSpinner.getValue.asInstanceOf[Int]

    def getInitialSelectionStrategy: InitialSelectionStrategy =
      if randomSamplingButton.isSelected() then
        RandomSampling
      else if uniformSamplingButton.isSelected() then
        UniformSampling
      else
        UniformChoice

    def getConvergenceStragegy: ConvergenceStrategy =
      if stepConvergenceButton.isSelected() then
        ConvergedAfterNSteps(stepCountSpinner.getValue.asInstanceOf[Int])
      else if etaConvergenceButton.isSelected() then
        ConvergedAfterMeansAreStill(etaCountSpinner.getValue.asInstanceOf[Double])
      else
        ConvergedWhenSNRAbove(snrCountSpinner.getValue.asInstanceOf[Int])

  try
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
  catch
    case _: Exception => println("Cannot set look and feel, using the default one.")

  val frame = ScalaShopFrame()

  def main(args: Array[String]): Unit =
    frame.repaint()


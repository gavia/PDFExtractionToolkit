package tools.ambitious.pdfextractiontoolkit.extractor

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.extraction.Extractor
import tools.ambitious.pdfextractiontoolkit.model.constraints.PageNumberConstraint
import tools.ambitious.pdfextractiontoolkit.model.geometry.{PositivePoint, Size}
import tools.ambitious.pdfextractiontoolkit.model._

class ExtractorSpec extends FreeSpec {
  val samplePDFPath = getClass.getResource("/simplePDFs/SimpleTest1Table.pdf")

  "An Extractor with a document and valid stencil" - {
    val document: Document = Document.fromPDFPath(samplePDFPath)

    val pageNumberConstraint: PageNumberConstraint = new PageNumberConstraint(1)
    val window: Window = Window.fromAbsoluteCoordinates(108, 81, 312, 305)
    window.addConstraint(pageNumberConstraint)

    val stencil: Stencil = new Stencil
    stencil.addWindow(window)

    val extractor: Extractor = Extractor.fromStencilAndDocument(stencil, document)

    "should be able to extract the table and have the value 10 in its top left cell" in {
      val tableMap: Map[Document, Table] = extractor.extractTables
      document.close()

      val table: Table = tableMap(document)
      val cell: Cell = table.getCell(1,1)

      assert(cell.text == "10")
    }
  }
}

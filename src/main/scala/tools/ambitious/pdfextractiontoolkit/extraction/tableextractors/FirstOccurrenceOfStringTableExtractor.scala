package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.extraction.{ExtractionUtils, StateBundle}
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}

case class FirstOccurrenceOfStringTableExtractor protected (text: String, textRegion: Rectangle, region: Rectangle) extends SimpleTableExtractor {

  override def onPage(page: Page, document: Document, stateBundle: StateBundle): Unit = {
    if (shouldExtractOnPage(page, document, stateBundle)) {
      stateBundle.state = Option.apply(page)
    }
  }

  override def tableFromState(stateBundle: StateBundle): Option[Table] = {
    if (stateBundle.state.isDefined) {
      val extractedTable: Table = performExtraction(stateBundle.state.asInstanceOf[Option[Page]].get, region)

      Option.apply(extractedTable)
    } else {
      Option.empty
    }
  }

  def shouldExtractOnPage(page: Page, document: Document, stateBundle: StateBundle): Boolean = {
    val table: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(page, textRegion)
    val foundText = table.getCell(0, 0).getText

    text == foundText && stateBundle.state.isEmpty
  }
}

object FirstOccurrenceOfStringTableExtractor {
  def withTextAndRegion(text: String, textRegion: Rectangle, region: Rectangle) = new FirstOccurrenceOfStringTableExtractor(text, textRegion, region)
}


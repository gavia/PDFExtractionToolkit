package tools.ambitious.pdfextractiontoolkit.model

import java.util

import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import org.scalatest.{PrivateMethodTester, FreeSpec}

class PageSpec extends FreeSpec {

  val twoPagedBlankDocumentPath = getClass.getResource("/simplePDFs/TwoPagedBlankDocument.pdf")

  "In the context of a PDDocument with two pages" - {
    val pDDocument: PDDocument = PDDocument.load(twoPagedBlankDocumentPath)
    val allPDPages: util.List[_] = pDDocument.getDocumentCatalog.getAllPages

    "A Page should instantiate from a single PDPage" in {
      val pDPage: PDPage = allPDPages.get(0).asInstanceOf[PDPage]
      Page.fromPDPage(pDPage)
    }

    "A List of two Pages that is converted from a List of two PDPages" - {
      val convertedPages: List[Page] = Page.listFromPDPageList(allPDPages)

      "should have the PDPage of its first page equal to the first PDPage" in {
        val firstPDPage: PDPage = allPDPages.get(0).asInstanceOf[PDPage]
        val firstPage: Page = convertedPages.head
        assert(firstPDPage == firstPage.pDPage)
      }

      "should have the PDPage of its second page equal to the second PDPage" in {
        val secondPDPage: PDPage = allPDPages.get(1).asInstanceOf[PDPage]
        val secondPage: Page = convertedPages(1)
        assert(secondPDPage == secondPage.pDPage)
      }

      "should not have the PDPage of its second page equal to the first PDPage" in {
        val firstPDPage: PDPage = allPDPages.get(0).asInstanceOf[PDPage]
        val secondPage: Page = convertedPages(1)
        assert(firstPDPage != secondPage.pDPage)
      }
    }
  }
}

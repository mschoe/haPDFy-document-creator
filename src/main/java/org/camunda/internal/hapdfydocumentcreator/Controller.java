package org.camunda.internal.hapdfydocumentcreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mschoe
 */
@RestController
@RequestMapping("pdf")
public class Controller {

  private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

  @Autowired
  PdfService pdfService;

  @RequestMapping(
          value = "/generateFromTemplate",
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public byte[] generateFromTemplate(@RequestBody PdfPayload pdfPayload) {
    LOGGER.log(Level.INFO, "Generate PDF from Template " + pdfPayload);

    try {
      return pdfService.createPdfFileFromTemplate(pdfPayload.getTemplate(), pdfPayload.getInputParameter());
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage() + " - PAYLOAD: " + pdfPayload);
      throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

}



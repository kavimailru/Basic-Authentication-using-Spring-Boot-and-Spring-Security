package com.Basic_Authentication.Basic_Authentication1.controller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.pirc.commerc.ObjectFactory;
import com.pirc.commerc.Документ;
import com.pirc.commerc.Документ.Контрагенты;
import com.pirc.commerc.КоммерческаяИнформация;
import com.pirc.commerc.КоммерческаяИнформация1;
import com.pirc.commerc.РольТип;
import com.pirc.commerc.ХозОперацияТип;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;


@RestController
public class WelComeController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam("type") String type, @RequestParam("mode") String mode) throws DatatypeConfigurationException{
System.out.println(type + " " + mode);
//        return "Spring Security In-memory Authentication Example - Welcome " + userName;
if (mode.equals("checkauth")){
        return "success\ntoken\n12345678qwerty";
} else {
	ObjectFactory objectFactory = new ObjectFactory();
    GregorianCalendar now = new GregorianCalendar();
    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    КоммерческаяИнформация  коммер = objectFactory.createКоммерческаяИнформация();
    коммер.setДатаФормирования(datatypeFactory.newXMLGregorianCalendar(now));
    коммер.setВерсияСхемы("2.03");
	Документ документ = objectFactory.createДокумент();
	документ.setХозОперация(ХозОперацияТип.ЗАКАЗ_ТОВАРА);
	документ.setИд("2223");
	документ.setНомер("1");
	документ.setДата(datatypeFactory.newXMLGregorianCalendar(now));
	документ.setРоль(РольТип.ПРОДАВЕЦ);
	документ.setВалюта("RUB");
	документ.setСумма(new BigDecimal(1000));
	документ.setКурс("1");
	Контрагенты контрагенты = new Контрагенты();
	Документ.Контрагенты.Контрагент контрагент = new Документ.Контрагенты.Контрагент();
	контрагент.getContent().add(objectFactory.createКонтрагентИд("12"));
	контрагент.getContent().add(objectFactory.createКонтрагентНаименование("Топаз"));
	контрагент.getContent().add(objectFactory.createКонтрагентПолноеНаименование("Топаз"));
	контрагент.getContent().add(objectFactory.createКонтрагентОфициальноеНаименование("Топаз"));
	контрагент.getContent().add(objectFactory.createКонтрагентИНН("5837069590"));
	контрагенты.getКонтрагент().add(контрагент);
	документ.setКонтрагенты(контрагенты);
	коммер.getДокумент().add(документ);
	
	URL res = getClass().getClassLoader().getResource("commerceml2.xsd");
	URL resxml = getClass().getClassLoader().getResource("zakaz.xml");

	try {
		File file = Paths.get(res.toURI()).toFile();
	    Schema schema = sf.newSchema(file);
		JAXBContext jContext = JAXBContext.newInstance(КоммерческаяИнформация.class);
	    Marshaller marshaller = jContext.createMarshaller();
	    Unmarshaller unmarshaller = jContext.createUnmarshaller();
	    unmarshaller.setSchema(schema);

	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // Optional: for pretty printing
	    marshaller.setSchema(schema);
	    marshaller.setEventHandler(new ValidationEventHandler() {
	        @Override
	        public boolean handleEvent(ValidationEvent event) {
	            // Handle validation errors or warnings
	            System.err.println("Validation marshaller Event: " + event.getMessage());
	            return true; // Return true to continue processing, false to stop
	        }
	    });
	    unmarshaller.setEventHandler(new ValidationEventHandler() {
	        @Override
	        public boolean handleEvent(ValidationEvent event) {
	            // Handle validation errors or warnings
	            System.err.println("Validation unmarshaller Event: " + event.getMessage());
	            return true; // Return true to continue processing, false to stop
	        }
	    });
	    StringWriter sw = new StringWriter();
	    КоммерческаяИнформация obj = (КоммерческаяИнформация) unmarshaller.unmarshal(resxml);
	    marshaller.marshal(obj , sw);
	    return sw.toString();
	} catch (JAXBException | SAXException | URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	System.out.println(type + " " + mode);
	return "";

}
    }

}

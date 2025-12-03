package com.Basic_Authentication.Basic_Authentication1.controller;
import java.io.OutputStream;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pirc.commerc.ObjectFactory;
import com.pirc.commerc.Документ;
import com.pirc.commerc.ХозОперацияТип;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;


@RestController
public class WelComeController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam("type") String type, @RequestParam("mode") String mode){
System.out.println(type + " " + mode);
//        return "Spring Security In-memory Authentication Example - Welcome " + userName;
if (mode.equals("checkauth")){
        return "success\ntoken\n12345678qwerty";
} else {
	ObjectFactory objectFactory = new ObjectFactory();
	Документ документ = objectFactory.createДокумент();
	документ.setХозОперация(ХозОперацияТип.СЧЕТ_НА_ОПЛАТУ);
	JAXBElement<Документ> jaxbElement = objectFactory.createДокумент(документ);
	try {
		JAXBContext jContext = JAXBContext.newInstance(Документ.class);
	    Marshaller marshallObj = jContext.createMarshaller();
	    marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    StringWriter sw = new StringWriter();
	    marshallObj.marshal(jaxbElement , sw);
	    return sw.toString();
	} catch (JAXBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	System.out.println(type + " " + mode);
	return "";

}
    }

}

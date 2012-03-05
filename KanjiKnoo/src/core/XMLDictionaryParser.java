package core;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import tools.Logger;

import dictionary.DictionaryFilesHandler;

public class XMLDictionaryParser {
	
	static final String KANJI = "character";
	static final String LITERAL = "literal";
	static final String GRADE = "grade";
	static final String STROKES = "stroke_count";
	static final String FREQUENCE = "freq";
	static final String JLPT = "jlpt";
	static final String CODEPOINT = "codepoint";
	static final String CP_VALUE = "cp_value";
	static final String ATTR_CP_TYPE = "cp_type"; 
	static final String MEANING = "meaning";
	static final String READING = "reading";
	static final String ATTR_R_TYPE = "r_type";
	static final String DIC_REF = "dic_ref";
	static final String ATTR_DIC_REF_TYPE = "dr_type";
	static final String ATTR_RAD_TYPE = "rad_type";
	static final String RAD_REF = "rad_value";


	@SuppressWarnings("unchecked")
	public List<Kanji> readDictionary(DictionaryFilesHandler fh) {
		List<Kanji> kanjis = new ArrayList<Kanji>();
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(fh.getDictionary(), "UTF-8");
			Kanji kanji = null;
			
			Integer i = 0;
			while(eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				i++;					
				
				if(event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					
					if(startElement.getName().getLocalPart() == (KANJI)) {
						kanji = new Kanji();
					}
					
					/**
					 * THE LITERAL CHAR
					 */
					if(event.isStartElement()) {
						if(event.asStartElement().getName().getLocalPart().equals(LITERAL)){
							event = eventReader.nextEvent();
							kanji.setLiteral(event.asCharacters().getData());
							continue;
						}
					}
					
					/**
					 * DICT REFS
					 */
					if(event.isStartElement()) {
						if(event.asStartElement().getName().getLocalPart().equals(DIC_REF)) {
							Iterator<Attribute> attributes = event.asStartElement().getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								if(attribute.getName().toString().equals(ATTR_DIC_REF_TYPE)) {
									event = eventReader.nextEvent();
									try {
										kanji.addDictRef(attribute.getValue(), Integer.parseInt(event.asCharacters().getData()));
									} catch (Exception e) {
										Logger.log(e.getMessage());
									}
								}
							}
							event = eventReader.nextEvent();
							continue;
						}
					}
					
					/**
					 * RADICALS
					 */
					if(event.isStartElement()) {
						if(event.asStartElement().getName().getLocalPart().equals(RAD_REF)) {
							Iterator<Attribute> attributes = event.asStartElement().getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								if(attribute.getName().toString().equals(ATTR_RAD_TYPE)) {
									event = eventReader.nextEvent();
									kanji.addRadical(attribute.getValue(), Integer.parseInt(event.asCharacters().getData()));
								}
							}
							event = eventReader.nextEvent();
							continue;
						}
					}
					
					/**
					 * READINGS
					 */
					if(event.isStartElement()) {
						if(event.asStartElement().getName().getLocalPart().equals(READING)) {
							Iterator<Attribute> attributes = event.asStartElement().getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								if(attribute.getName().toString().equals(ATTR_R_TYPE)) {
									if(attribute.getValue().equalsIgnoreCase("ja_kun")) {
										event = eventReader.nextEvent();
										kanji.addKunReading(event.asCharacters().getData());
										continue;
									} else if(attribute.getValue().equalsIgnoreCase("ja_on")) {
										event = eventReader.nextEvent();
										kanji.addOnReading(event.asCharacters().getData());
										continue;
									}
								}
							}
							event = eventReader.nextEvent();
							continue;
						}
					}
					
					if(event.isStartElement()) {
						if(event.asStartElement().getName().getLocalPart().equals(CP_VALUE)) {
							Iterator<Attribute> attributes = event.asStartElement().getAttributes();
							while(attributes.hasNext()) {
								Attribute attribute = attributes.next();
								if(attribute.getName().toString().equals(ATTR_CP_TYPE)) {
									if(attribute.getValue().equalsIgnoreCase("ucs")) {
										event = eventReader.nextEvent();
										kanji.setUcs(event.asCharacters().getData());
										continue;
									} else if(attribute.getValue().equalsIgnoreCase("jis201")) {
										event = eventReader.nextEvent();
										kanji.setJis201(event.asCharacters().getData());
										continue;
									} else if(attribute.getValue().equalsIgnoreCase("jis208")) {
										event = eventReader.nextEvent();
										kanji.setJis208(event.asCharacters().getData());
										continue;
									} else if(attribute.getValue().equalsIgnoreCase("jis212")) {
										event = eventReader.nextEvent();
										kanji.setJis212(event.asCharacters().getData());
										continue;
									} else if(attribute.getValue().equalsIgnoreCase("jis213")) {
										event = eventReader.nextEvent();
										kanji.setJis213(event.asCharacters().getData());
										continue;
									}
								}
							}
							event = eventReader.nextEvent();
							continue;
						}
					}
					
					if(event.asStartElement().getName().getLocalPart().equals(MEANING)) {
						if(!event.asStartElement().getAttributes().hasNext()) {
							event = eventReader.nextEvent();
							kanji.addMeaning(event.asCharacters().getData());
							continue;
						}
					}
					if(event.asStartElement().getName().getLocalPart().equals(GRADE)) {
						event = eventReader.nextEvent();
						kanji.setGrade(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}
					if(event.asStartElement().getName().getLocalPart().equals(STROKES)) {
						event = eventReader.nextEvent();
						kanji.setStrokeCount(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}	
					if(event.asStartElement().getName().getLocalPart().equals(FREQUENCE)) {
						event = eventReader.nextEvent();
						kanji.setFrequence(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}
					if(event.asStartElement().getName().getLocalPart().equals(JLPT)) {
						event = eventReader.nextEvent();
						kanji.setJlpt(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}
				}
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (KANJI)) {
						kanjis.add(kanji);
					}
				}
			}
			
		} catch (XMLStreamException e) {
			Logger.log("Dictinary warning: " + e.getMessage());
		}
		return kanjis;
	}
}

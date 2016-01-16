package yy.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import yy.songmodel.SongInfo;

public class SongsHandler extends DefaultHandler {
	private List<SongInfo> infos ;
	private String tagName;
	private SongInfo info;
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.tagName = localName;
		if(tagName.equals("song")){
			info = new SongInfo();
		}
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("song")){
			System.out.println("-------------->"+info.toString());
			infos.add(info);
		}
		tagName = "";
		super.endElement(uri, localName, qName);
	}

	public SongsHandler(List<SongInfo> infos) {
		super();
		this.infos = infos;
	}

	public List<SongInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<SongInfo> infos) {
		this.infos = infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp = new String(ch,start,length);
			if(tagName.equals("id")){
				info.setId(temp);
			}
			else if(tagName.equals("name")){
				info.setName(temp);
			}
			else if(tagName.equals("size")){
				info.setSize(temp);
			}
			else if(tagName.equals("singer")){
				info.setSinger(temp);
			}
			super.characters(ch, start, length);
	}
	
}

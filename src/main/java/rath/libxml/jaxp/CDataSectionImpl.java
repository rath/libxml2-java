package rath.libxml.jaxp;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import rath.libxml.Node;

/**
 * User: rath
 * Date: 17/11/2013
 * Time: 18:14
 */
public class CDATASectionImpl extends TextImpl implements CDATASection {
	protected CDATASectionImpl(Document owner, Node impl) {
		super(owner, impl);
	}
}

package org.xmlsoft.jaxp;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.xmlsoft.Node;

/**
 * User: rath
 * Date: 17/11/2013
 * Time: 18:07
 */
public class CommentImpl extends CharacterDataImpl implements Comment {
	protected CommentImpl(Document owner, Node impl) {
		super(owner, impl);
	}
}

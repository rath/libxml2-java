package org.xmlsoft.jaxp;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.ProcessingInstruction;
import org.xmlsoft.Node;

/**
 * User: rath
 * Date: 17/11/2013
 * Time: 22:32
 */
public class ProcessingInstructionImpl extends NodeImpl implements ProcessingInstruction {
	protected ProcessingInstructionImpl(Document owner, Node impl) {
		super(owner, impl);
	}

	@Override
	public String getTarget() {
		return impl.getName();
	}

	@Override
	public String getData() {
		return impl.getText();
	}

	@Override
	public void setData(String data) throws DOMException {
		impl.setText(data);
	}
}

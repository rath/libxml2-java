#include "org_xmlsoft_XPathExpression.h"
#include <libxml/tree.h>
#include <libxml/xpath.h>
#include "cache.h"


/*
 * Class:     org_xmlsoft_XPathExpression
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_XPathExpression_disposeImpl
(JNIEnv *env, jobject obj) {
    xmlXPathCompExpr *compiled = (xmlXPathCompExprPtr)(*env)->GetLongField(env, obj, fieldXPathExprP);
    xmlXPathFreeCompExpr(compiled);
}
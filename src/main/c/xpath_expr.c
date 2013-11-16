#include "rath_libxml_XPathExpression.h"
#include <libxml/tree.h>
#include <libxml/xpath.h>
#include "cache.h"


/*
 * Class:     rath_libxml_XPathExpression
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_XPathExpression_disposeImpl
(JNIEnv *env, jobject obj) {
    xmlXPathCompExpr *compiled = (xmlXPathCompExprPtr)(*env)->GetLongField(env, obj, fieldXPathExprP);
    xmlXPathFreeCompExpr(compiled);
}
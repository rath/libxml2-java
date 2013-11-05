#include "rath_libxml_XPathContext.h"
#include <libxml/tree.h>
#include <libxml/xpath.h>
#include "utils.h"
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

/*
 * Class:     rath_libxml_XPathContext
 * Method:    evaluateImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/XPathObject;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_XPathContext_evaluateImpl
(JNIEnv *env, jobject obj, jstring jexpr) {
    xmlXPathContext *ctx = findXPathContext(env, obj);
    xmlXPathObject *result;

    const char *expr = (*env)->GetStringUTFChars(env, jexpr, NULL);
    result = xmlXPathEvalExpression((const xmlChar*)expr, ctx);

    if(result==NULL) {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "rath/libxml/InvalidXPathExpressionException"), expr);
        (*env)->ReleaseStringUTFChars(env, jexpr, expr);
        return NULL;
    }
    (*env)->ReleaseStringUTFChars(env, jexpr, expr);

    jobject resultObject = createPointerObject(env, "rath/libxml/XPathObject", (jlong)result);
    jclass resultClass = (*env)->GetObjectClass(env, resultObject);
    jfieldID valueFieldId;
    
    switch(result->type) {
    case XPATH_NODESET:
        valueFieldId = (*env)->GetFieldID(env, resultClass, "nodeset", "Lrath/libxml/NodeSet;");
        (*env)->SetObjectField(env, resultObject, valueFieldId, buildNodeSet(env, result->nodesetval, obj));
    break;
    case XPATH_BOOLEAN:
        valueFieldId = (*env)->GetFieldID(env, resultClass, "booleanValue", "Z");
        (*env)->SetBooleanField(env, resultObject, valueFieldId, result->boolval==0 ? JNI_FALSE : JNI_TRUE);
    break;
    case XPATH_NUMBER:
        valueFieldId = (*env)->GetFieldID(env, resultClass, "floatValue", "D");
        (*env)->SetDoubleField(env, resultObject, valueFieldId, result->floatval);
    break;
    case XPATH_STRING:
        valueFieldId = (*env)->GetFieldID(env, resultClass, "stringValue", "Ljava/lang/String;");
        (*env)->SetObjectField(env, resultObject, valueFieldId, (*env)->NewStringUTF(env, (char*)result->stringval));
    break;
    case XPATH_UNDEFINED:
//        assert(0);
    break;
    case XPATH_POINT:
//        assert(0);
    break;
    case XPATH_RANGE:
//        assert(0);
    break;
    case XPATH_LOCATIONSET:
//        assert(0);
    break;
    case XPATH_USERS:
//        assert(0);
    break;
    case XPATH_XSLT_TREE:
//        assert(0);
    break;
    }

    return resultObject;
}

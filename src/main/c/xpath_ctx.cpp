#include "rath_libxml_XPathContext.h"
#include <libxml/tree.h>
#include <libxml/xpath.h>
#include "utils.h"
#include <assert.h>

/*
 * Class:     rath_libxml_XPathContext
 * Method:    evaluateImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/XPathObject;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_XPathContext_evaluateImpl
  (JNIEnv *env, jobject obj, jstring jexpr) {
    xmlXPathContext *ctx = findXPathContext(env, obj);
    xmlXPathObject *result;

    const char *expr = env->GetStringUTFChars(jexpr, NULL);
    result = xmlXPathEvalExpression((const xmlChar*)expr, ctx);

    if(result==NULL) {
        env->ThrowNew(env->FindClass("rath/libxml/InvalidXPathExpressionException"), expr);
        env->ReleaseStringUTFChars(jexpr, expr);
        return NULL;
    }
    env->ReleaseStringUTFChars(jexpr, expr);

    jobject resultObject = createPointerObject(env, "rath/libxml/XPathObject", (jlong)result);
    jclass resultClass = env->GetObjectClass(resultObject);
    jfieldID valueFieldId;

    switch(result->type) {
    case XPATH_NODESET:
        valueFieldId = env->GetFieldID(resultClass, "nodeset", "Lrath/libxml/NodeSet;");
        env->SetObjectField(resultObject, valueFieldId, buildNodeSet(env, result->nodesetval, obj));
    break;
    case XPATH_BOOLEAN:
        valueFieldId = env->GetFieldID(resultClass, "booleanValue", "Z");
        env->SetBooleanField(resultObject, valueFieldId, result->boolval==0 ? JNI_FALSE : JNI_TRUE);
    break;
    case XPATH_NUMBER:
        valueFieldId = env->GetFieldID(resultClass, "floatValue", "D");
        env->SetDoubleField(resultObject, valueFieldId, result->floatval);
    break;
    case XPATH_STRING:
        valueFieldId = env->GetFieldID(resultClass, "stringValue", "Ljava/lang/String;");
        env->SetObjectField(resultObject, valueFieldId, env->NewStringUTF((char*)result->stringval));
    break;
    case XPATH_UNDEFINED:
        assert(0);
    break;
    case XPATH_POINT:
        assert(0);
    break;
    case XPATH_RANGE:
        assert(0);
    break;
    case XPATH_LOCATIONSET:
        assert(0);
    break;
    case XPATH_USERS:
        assert(0);
    break;
    case XPATH_XSLT_TREE:
        assert(0);
    break;
    }

    return resultObject;
}

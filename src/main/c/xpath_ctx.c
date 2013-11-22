#include "org_xmlsoft_XPathContext.h"
#include <libxml/parser.h>
#include <libxml/tree.h>
#include <libxml/xpath.h>
#include <libxml/xpathInternals.h>
#include "cache.h"
#include "utils.h"
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

jobject buildXPathObject(JNIEnv *env, jobject xpathContext, xmlXPathObject *result) {
    jobject resultObject = (*env)->NewObject(env, classXPathObject, methodXPathObjectNew, (jlong)result, xmlXPathNodeSetIsEmpty(result->nodesetval));
    jobject jdoc;
    
    switch(result->type) {
    case XPATH_NODESET:
        jdoc = (*env)->GetObjectField(env, xpathContext, fieldXPathContextDocument);
        (*env)->SetObjectField(env, resultObject, fieldXPathObjectSetNodeset, buildNodeSet(env, result->nodesetval, jdoc));
        (*env)->DeleteLocalRef(env, jdoc);
    break;
    case XPATH_BOOLEAN:
        (*env)->SetBooleanField(env, resultObject, fieldXPathObjectSetBool, result->boolval==0 ? JNI_FALSE : JNI_TRUE);
    break;
    case XPATH_NUMBER:
        (*env)->SetDoubleField(env, resultObject, fieldXPathObjectSetFloat, result->floatval);
    break;
    case XPATH_STRING:
        (*env)->SetObjectField(env, resultObject, fieldXPathObjectSetString, (*env)->NewStringUTF(env, (char*)result->stringval));
    break;
    case XPATH_UNDEFINED:
        assert(0 && "XPATH_UNDEFIEND not implemented");
    break;
    case XPATH_POINT:
        assert(0 && "XPATH_POINT not implemented");
    break;
    case XPATH_RANGE:
        assert(0 && "XPATH_RANGE not implemented");
    break;
    case XPATH_LOCATIONSET:
        assert(0 && "XPATH_LOCATIONSET not implemented");
    break;
    case XPATH_USERS:
        assert(0 && "XPATH_USERS not implemented");
    break;
    case XPATH_XSLT_TREE:
        assert(0 && "XPATH_XSLT_TREE not implemented");
    break;
    }

    return resultObject;
}

/*
 * Class:     org_xmlsoft_XPathContext
 * Method:    evaluateImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/XPathObject;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_XPathContext_evaluateImpl
(JNIEnv *env, jobject obj, jstring jexpr) {
    xmlXPathContext *ctx = findXPathContext(env, obj);
    xmlXPathObject *result;

    const char *expr = (*env)->GetStringUTFChars(env, jexpr, NULL);
    result = xmlXPathEvalExpression((const xmlChar*)expr, ctx);
    if(result==NULL) {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "org/xmlsoft/XPathExpressionException"), expr);
        (*env)->ReleaseStringUTFChars(env, jexpr, expr);
        return NULL;
    }
    (*env)->ReleaseStringUTFChars(env, jexpr, expr);

    return buildXPathObject(env, obj, result);
}

/*
 * Class:     org_xmlsoft_XPathContext
 * Method:    evaluateCompiledImpl
 * Signature: (Lrath/libxml/XPathExpression;)Lrath/libxml/XPathObject;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_XPathContext_evaluateCompiledImpl
(JNIEnv *env, jobject obj, jobject expr) {
    xmlXPathContext *ctx = findXPathContext(env, obj);
    
    xmlXPathCompExpr *compiled = (xmlXPathCompExprPtr)(*env)->GetLongField(env, expr, fieldXPathExprP);
    xmlXPathObject *result = xmlXPathCompiledEval(compiled, ctx);
    return buildXPathObject(env, obj, result);
}

/*
 * Class:     org_xmlsoft_XPathContext
 * Method:    addNamespaceImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_XPathContext_addNamespaceImpl
(JNIEnv *env, jobject obj, jstring jPrefix, jstring jHref) {
    xmlXPathContext *ctx = findXPathContext(env, obj);
    const char *prefix = NULL;
    const char *href = NULL;
    int ret;
    
    if( jPrefix!=NULL )
        prefix = (*env)->GetStringUTFChars(env, jPrefix, NULL);
    if( jHref!=NULL )
        href = (*env)->GetStringUTFChars(env, jHref, NULL);
    
    ret = xmlXPathRegisterNs(ctx, (const xmlChar*)prefix, (const xmlChar*)href);
    
    if( jPrefix!=NULL )
        (*env)->ReleaseStringUTFChars(env, jPrefix, prefix);
    if( jHref!=NULL )
        (*env)->ReleaseStringUTFChars(env, jHref, href);
    
    if( ret ) {
        if(!throwInternalErrorWithLastError(env)) {
            throwInternalErrorWithMessage(env, "xmlXPathRegisterNs");
        }
    }
}

/*
 * Class:     org_xmlsoft_XPathContext
 * Method:    setContextNodeImpl
 * Signature: (Lrath/libxml/Node;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_XPathContext_setContextNodeImpl
(JNIEnv *env, jobject obj, jobject jnode) {
    if( jnode==NULL )
        return;
    xmlXPathContext *ctx = findXPathContext(env, obj);
    xmlNode *node = findNode(env, jnode);
    
    if(node==NULL || ctx==NULL || node->doc!=ctx->doc) {
        throwInternalErrorWithMessage(env, "context node must be child in document");
    } else {
        ctx->node = node;
    }
}

/*
 * Class:     org_xmlsoft_XPathContext
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_XPathContext_disposeImpl
(JNIEnv *env, jobject obj) {
    xmlXPathContext *ctx = findXPathContext(env, obj);
    xmlXPathFreeContext(ctx);
}


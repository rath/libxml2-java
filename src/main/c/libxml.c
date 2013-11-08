#include "rath_libxml_LibXml.h"
#include <libxml/parser.h>
#include <string.h>
#include <stdarg.h>
#include <assert.h>
#include "cache.h"
#include "utils.h"

jclass classError;
jclass classDocument;
jclass classNode;
jclass classNodeset;
jclass classNamespace;
jclass classXPathContext;
jclass classXPathObject;

jmethodID methodErrorNew;
jmethodID methodDocumentNew;
jmethodID methodNodeNew;
jmethodID methodNodesetNew;
jmethodID methodNodesetAddNode;
jmethodID methodNamespaceNew;
jmethodID methodXPathContextNew;
jmethodID methodXPathObjectNew;

jmethodID methodNodeSetType;
jmethodID methodNodeSetDocument;

jmethodID methodListAdd;

jfieldID fieldDocumentGetP;
jfieldID fieldNodeGetP;
jfieldID fieldNodeDocument;
jfieldID fieldNodeSetName;
jfieldID fieldNodeSetNamespace;
jfieldID fieldNodesetSize;
jfieldID fieldXPathContextGetP;
jfieldID fieldXPathContextDocument;
jfieldID fieldXPathObjectGetP;
jfieldID fieldXPathObjectSetNodeset;
jfieldID fieldXPathObjectSetBool;
jfieldID fieldXPathObjectSetFloat;
jfieldID fieldXPathObjectSetString;

static void cc(JNIEnv *env, const char *name, jclass *buf) {
    jclass c = (*env)->FindClass(env, name);
    assert(c && "Finding class failed");
    *buf = (*env)->NewGlobalRef(env, c);
    (*env)->DeleteLocalRef(env, c);
}

static void handlerGenericError(void *ctx, const char *msg, ...) {
    /*
    JNIEnv *env = (JNIEnv*)ctx;
    va_list args;
    va_start(args, msg);
    vfprintf(stdout, msg, args);
    va_end(args);
     */
    return;
}

static void handlerStructuredError(void *ctx, xmlErrorPtr error) {
    /*
    JNIEnv *env = (JNIEnv*)ctx;
    */
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    initInternalParser
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_LibXml_initInternalParser
(JNIEnv *env, jclass clz) {
    
    // http://xmlsoft.org/threads.html
    // >> call xmlInitParser() in the "main" thread before using any of the libxml2 API (except possibly selecting a different memory allocator)
    // TODO: Could Java ensure init this method by main thread rather than 'Java main thread'?
    xmlInitParser();
    
    xmlSetGenericErrorFunc(env, handlerGenericError);
    xmlSetStructuredErrorFunc(env, handlerStructuredError);
    
    cc(env, "rath/libxml/LibXmlException", &classError);
    cc(env, "rath/libxml/Document", &classDocument);
    cc(env, "rath/libxml/Node", &classNode);
    cc(env, "rath/libxml/NodeSet", &classNodeset);
    cc(env, "rath/libxml/Namespace", &classNamespace);
    cc(env, "rath/libxml/XPathContext", &classXPathContext);
    cc(env, "rath/libxml/XPathObject", &classXPathObject);
   
    methodErrorNew = (*env)->GetMethodID(env, classError, "<init>", "(ILjava/lang/String;II)V");
    methodDocumentNew = (*env)->GetMethodID(env, classDocument, "<init>", "(J)V");
    methodNodeNew = (*env)->GetMethodID(env, classNode, "<init>", "(J)V");
    methodNodesetNew = (*env)->GetMethodID(env, classNodeset, "<init>", "(J)V");
    methodNodesetAddNode = (*env)->GetMethodID(env, classNodeset, "addNode", "(Lrath/libxml/Node;)V");
    methodNamespaceNew = (*env)->GetMethodID(env, classNamespace, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
    methodXPathContextNew = (*env)->GetMethodID(env, classXPathContext, "<init>", "(J)V");
    methodXPathObjectNew = (*env)->GetMethodID(env, classXPathObject, "<init>", "(J)V");
    
    methodNodeSetType = (*env)->GetMethodID(env, classNode, "setType", "(I)V");
    methodNodeSetDocument = (*env)->GetMethodID(env, classNode, "setDocument", "(Lrath/libxml/Document;)V");
    
    fieldDocumentGetP = (*env)->GetFieldID(env, classDocument, "p", "J");
    fieldNodeGetP = (*env)->GetFieldID(env, classNode, "p", "J");
    fieldNodeDocument = (*env)->GetFieldID(env, classNode, "document", "Lrath/libxml/Document;");
    fieldNodeSetName = (*env)->GetFieldID(env, classNode, "name", "Ljava/lang/String;");
    fieldNodeSetNamespace = (*env)->GetFieldID(env, classNode, "namespace", "Lrath/libxml/Namespace;");
    fieldNodesetSize = (*env)->GetFieldID(env, classNodeset, "size", "I");
    fieldXPathContextGetP = (*env)->GetFieldID(env, classXPathContext, "p", "J");
    fieldXPathContextDocument = (*env)->GetFieldID(env, classXPathContext, "document", "Lrath/libxml/Document;");
    fieldXPathObjectGetP = (*env)->GetFieldID(env, classXPathObject, "p", "J");
    fieldXPathObjectSetNodeset = (*env)->GetFieldID(env, classXPathObject, "nodeset", "Lrath/libxml/NodeSet;");
    fieldXPathObjectSetBool = (*env)->GetFieldID(env, classXPathObject, "booleanValue", "Z");
    fieldXPathObjectSetFloat = (*env)->GetFieldID(env, classXPathObject, "floatValue", "D");
    fieldXPathObjectSetString = (*env)->GetFieldID(env, classXPathObject, "stringValue", "Ljava/lang/String;");
    
    jclass classList = (*env)->FindClass(env, "java/util/List");
    methodListAdd = (*env)->GetMethodID(env, classList, "add", "(Ljava/lang/Object;)Z");
    
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseFileImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Document;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_LibXml_parseFileImpl
(JNIEnv *env, jclass clazz, jstring filepath) {
    const char *path = (*env)->GetStringUTFChars(env, filepath, NULL);
    xmlDoc *doc = xmlParseFile(path);
    (*env)->ReleaseStringUTFChars(env, filepath, path);
    assert(doc && "parsing(xmlParseFile) failed");
    return buildDocument(env, doc);
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseStringImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Document;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_LibXml_parseStringImpl
(JNIEnv *env, jclass clazz, jstring jdata) {
    const char *data = (*env)->GetStringUTFChars(env, jdata, NULL);
    size_t datalen = strlen(data);
    xmlDoc *doc = xmlReadMemory(data, (int)datalen, "<>", "UTF8", 0); // TODO: Handling xmlParserOption
    (*env)->ReleaseStringUTFChars(env, jdata, data);
    
    if(doc==NULL) {
        throwInternalErrorWithLastError(env);
        return NULL;
    }
    assert(doc && "parsing(xmlReadMemory) failed but didn't throw error");
    return buildDocument(env, doc);
}
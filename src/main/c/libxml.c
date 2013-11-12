#include "rath_libxml_LibXml.h"
#include <libxml/parser.h>
#include <string.h>
#include <stdarg.h>
#include <stdlib.h>
#include <assert.h>
#include "cache.h"
#include "utils.h"

#define CHUNK_SIZE 256

jclass classString;
jclass classError;
jclass classDocument;
jclass classNode;
jclass classNodeset;
jclass classNamespace;
jclass classXPathContext;
jclass classXPathObject;
jclass classLocator;
jclass classInputStream;
jclass classAttribute;

jmethodID methodErrorNew;
jmethodID methodDocumentNew;
jmethodID methodNodeNew;
jmethodID methodNodesetNew;
jmethodID methodNodesetAddNode;
jmethodID methodNamespaceNew;
jmethodID methodXPathContextNew;
jmethodID methodXPathObjectNew;
jmethodID methodLocatorNew;
jmethodID methodInputStreamRead;
jmethodID methodAttributeNew;

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
jfieldID fieldLocatorP;

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
    return;
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
    
    cc(env, "java/lang/String", &classString);
    cc(env, "rath/libxml/LibXmlException", &classError);
    cc(env, "rath/libxml/Document", &classDocument);
    cc(env, "rath/libxml/Node", &classNode);
    cc(env, "rath/libxml/NodeSet", &classNodeset);
    cc(env, "rath/libxml/Namespace", &classNamespace);
    cc(env, "rath/libxml/XPathContext", &classXPathContext);
    cc(env, "rath/libxml/XPathObject", &classXPathObject);
    cc(env, "rath/libxml/impl/LocatorImpl", &classLocator);
    cc(env, "java/io/InputStream", &classInputStream);
    cc(env, "rath/libxml/Attribute", &classAttribute);
   
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
    methodLocatorNew = (*env)->GetMethodID(env, classLocator, "<init>", "(J)V");
    methodAttributeNew = (*env)->GetStaticMethodID(env, classAttribute, "createInstance", "(Lrath/libxml/Namespace;Ljava/lang/String;Ljava/lang/String;)Lrath/libxml/Attribute;");
    methodInputStreamRead = (*env)->GetMethodID(env, classInputStream, "read", "([BII)I");
    
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
    fieldLocatorP = (*env)->GetFieldID(env, classLocator, "p", "J");
    
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
    xmlResetLastError();
    xmlDoc *doc = xmlParseFile(path);
    (*env)->ReleaseStringUTFChars(env, filepath, path);
    
    if(doc==NULL) {
        throwInternalErrorWithLastError(env);
        return NULL;
    }
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
    xmlResetLastError();
    xmlDoc *doc = xmlReadMemory(data, (int)datalen, "<>", "UTF8", 0); // TODO: Handling xmlParserOption
    (*env)->ReleaseStringUTFChars(env, jdata, data);
    
    if(doc==NULL) {
        throwInternalErrorWithLastError(env);
        return NULL;
    }
    
    xmlNs *ns = doc->oldNs;
    while(ns!=NULL) {
        fprintf(stdout, "uri: %s prefix: %s\n", ns->href, ns->prefix);
        ns = ns->next;
    }
    
    assert(doc && "parsing(xmlReadMemory) failed but didn't throw error");
    return buildDocument(env, doc);
}

/*
struct _xmlSAXHandler {
    internalSubsetSAXFunc internalSubset;
    isStandaloneSAXFunc isStandalone;
    hasInternalSubsetSAXFunc hasInternalSubset;
    hasExternalSubsetSAXFunc hasExternalSubset;
    resolveEntitySAXFunc resolveEntity;
    getEntitySAXFunc getEntity;
    entityDeclSAXFunc entityDecl;
*    notationDeclSAXFunc notationDecl;
    attributeDeclSAXFunc attributeDecl;
    elementDeclSAXFunc elementDecl;
*    unparsedEntityDeclSAXFunc unparsedEntityDecl;
/    setDocumentLocatorSAXFunc setDocumentLocator;
*    startDocumentSAXFunc startDocument;
*    endDocumentSAXFunc endDocument;
#    startElementSAXFunc startElement;
#    endElementSAXFunc endElement;
    referenceSAXFunc reference;
*    charactersSAXFunc characters;
*    ignorableWhitespaceSAXFunc ignorableWhitespace;
*    processingInstructionSAXFunc processingInstruction;
L   commentSAXFunc comment;
*   warningSAXFunc warning;
*   errorSAXFunc error;
*   fatalErrorSAXFunc fatalError;
    getParameterEntitySAXFunc getParameterEntity;
L   cdataBlockSAXFunc cdataBlock;
    externalSubsetSAXFunc externalSubset;
    unsigned int initialized;
    void *_private;
*    startElementNsSAX2Func startElementNs;
*    endElementNsSAX2Func endElementNs;
    xmlStructuredErrorFunc serror;
};
*/

typedef struct {
    JNIEnv *env;
    jobject handler;
    jmethodID midStartDocument;
    jmethodID midEndDocument;
    jmethodID midEnsureChars;
    jmethodID midCharacters;
    jmethodID midIgnorableWhitespace;
    jmethodID midStartElement;
    jmethodID midEndElement;
    jmethodID midProcessingInstruction;
    jmethodID midNotationDecl;
    jmethodID midUnparsedEntityDecl;
    jmethodID midWarning;
    jmethodID midError;
    jmethodID midFatalError;
    jmethodID midSetLocator;
    LocatorContext *locatorContext;
} SContext;

static void _startDocument(void *p) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midStartDocument);
    // TODO: should check exceptionOccurs then stop internal parsing by calling xmlStopParser(p);
}

static void _ensureChars(SContext *ctx, const xmlChar *ch, int len) {
    JNIEnv *env = ctx->env;
    jbyteArray array = (*env)->CallObjectMethod(env, ctx->handler, ctx->midEnsureChars, (jint)len);
    jboolean isCopy;
    jbyte *jbuffer = (*env)->GetPrimitiveArrayCritical(env, array, &isCopy);
    memcpy(jbuffer, ch, len);
    (*env)->ReleasePrimitiveArrayCritical(env, array, jbuffer, 0);
    (*env)->DeleteLocalRef(env, array);
}

static void _characters(void *p, const xmlChar *ch, int len) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    _ensureChars(ctx, ch, len);
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midCharacters);
    // TODO: should check exceptionOccurs then stop internal parsing by calling xmlStopParser(p);
}

static void _ignorableWhitespace(void *p, const xmlChar *ch, int len) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    _ensureChars(ctx, ch, len);
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midIgnorableWhitespace);
}

static void _startElementNs(void *p, const xmlChar *localname, const xmlChar *prefix, const xmlChar *uri,
                            int nb_namespaces, const xmlChar **namespaces,
                            int nb_attributes, int nb_defaulted, const xmlChar **attributes) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    
    int i;
    jobjectArray jNs = NULL;
    jobjectArray jAttr = NULL;
    
    if( nb_namespaces>0 ) {
        // prefix/uri
        jNs = (*env)->NewObjectArray(env, nb_namespaces*2, classString, NULL);
        for(i=0; i<nb_namespaces; i++) {
            (*env)->SetObjectArrayElement(env, jNs, 2*i+0, (*env)->NewStringUTF(env, (char*)namespaces[2*i+0]));
            (*env)->SetObjectArrayElement(env, jNs, 2*i+1, (*env)->NewStringUTF(env, (char*)namespaces[2*i+1]));
        }
    }
    if( nb_attributes>0 ) {
        // localname/prefix/URI/value/end
        jAttr = (*env)->NewObjectArray(env, nb_attributes*4, classString, NULL);
        for(i=0; i<nb_attributes; i++) {
            (*env)->SetObjectArrayElement(env, jAttr, 4*i+0, (*env)->NewStringUTF(env, (char*)attributes[5*i+0]));
            (*env)->SetObjectArrayElement(env, jAttr, 4*i+1, (*env)->NewStringUTF(env, (char*)attributes[5*i+1]));
            (*env)->SetObjectArrayElement(env, jAttr, 4*i+2, (*env)->NewStringUTF(env, (char*)attributes[5*i+2]));
            size_t value_len = attributes[5*i+4] - attributes[5*i+3];
            char *value = (char*)malloc(sizeof(char) * (value_len+1));
            strncpy(value, (const char*)attributes[5*i+3], value_len);
            value[value_len] = 0;
            (*env)->SetObjectArrayElement(env, jAttr, 4*i+3, (*env)->NewStringUTF(env, value));
            free(value);
        }
    }

    (*env)->CallVoidMethod(env, ctx->handler, ctx->midStartElement,
                           (*env)->NewStringUTF(env, (const char*)uri),
                           (*env)->NewStringUTF(env, (const char*)prefix),
                           (*env)->NewStringUTF(env, (const char*)localname),
                           jNs, jAttr, (jint)nb_defaulted);
    
    (*env)->DeleteLocalRef(env, jNs);
    (*env)->DeleteLocalRef(env, jAttr);
}

static void _endElementNs(void *p, const xmlChar *localname, const xmlChar *prefix, const xmlChar *uri) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    
     (*env)->CallVoidMethod(env, ctx->handler, ctx->midEndElement,
                           (*env)->NewStringUTF(env, (const char*)uri),
                           (*env)->NewStringUTF(env, (const char*)prefix),
                           (*env)->NewStringUTF(env, (const char*)localname));
}

/*
 * It will never be called because we use XML_SAX2_MAGIC.
static void _startElement(void *p, const xmlChar *name, const xmlChar **atts) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private; JNIEnv *env = ctx->env;
    
    fprintf(stdout, "_startElement: name=%s\n", name);
    if(atts!=NULL) {
        int i = 0;
        while(1) {
            xmlChar *name = (xmlChar*)atts[i];
            xmlChar *value = (xmlChar*)atts[i+1];
            fprintf(stdout, "%s = %s\n", name, value);
            i+=2;
            if (atts[i]==NULL) break;
        }
    } else {
        fprintf(stdout, "no attributes\n");
    }
}
 */

static void _endDocument(void *p) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midEndDocument);
}

static void _processingInstruction(void *p, const xmlChar *target, const xmlChar *data) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midProcessingInstruction,
                           (*env)->NewStringUTF(env, (const char*)target),
                           (*env)->NewStringUTF(env, (const char*)data));
}

static void _notationDecl(void *p, const xmlChar *name, const xmlChar *publicId, const xmlChar *systemId) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midNotationDecl,
                           (*env)->NewStringUTF(env, (const char*)name),
                           (*env)->NewStringUTF(env, (const char*)publicId),
                           (*env)->NewStringUTF(env, (const char*)systemId));
}

static void _unparsedEntityDecl(void *p, const xmlChar *name, const xmlChar *publicId, const xmlChar *systemId, const xmlChar *notationName) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midNotationDecl,
                           (*env)->NewStringUTF(env, (const char*)name),
                           (*env)->NewStringUTF(env, (const char*)publicId),
                           (*env)->NewStringUTF(env, (const char*)systemId),
                           (*env)->NewStringUTF(env, (const char*)notationName));
}

static void _warning(void *p, const char *msg, ...) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    char buf[512];
    
    va_list arg;
    va_start(arg, msg);
    vsprintf(buf, msg, arg);
    va_end(arg);
    
    // TODO: working with xmlGetLastError()?
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midWarning, (*env)->NewStringUTF(env, buf));
}

static void _error(void *p, const char *msg, ...) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    char buf[512];
    
    va_list arg;
    va_start(arg, msg);
    vsprintf(buf, msg, arg);
    va_end(arg);
    
    // TODO: working with xmlGetLastError()?
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midError, (*env)->NewStringUTF(env, buf));
}

static void _fatalError(void *p, const char *msg, ...) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    char buf[512];
    
    va_list arg;
    va_start(arg, msg);
    vsprintf(buf, msg, arg);
    va_end(arg);
    
    // TODO: working with xmlGetLastError()?
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midFatalError, (*env)->NewStringUTF(env, buf));
}

static void _setDocumentLocator(void *p, xmlSAXLocatorPtr loc) {
    SContext *ctx = (SContext*)((xmlParserCtxt*)p)->_private;
    JNIEnv *env = ctx->env;
    
    ctx->locatorContext = (LocatorContext*)malloc(sizeof(LocatorContext));
    ctx->locatorContext->parser = (xmlParserCtxt*)p;
    ctx->locatorContext->locator = loc;
    
    jobject locator = (*env)->NewObject(env, classLocator, methodLocatorNew, (jlong)ctx->locatorContext);
    (*env)->CallVoidMethod(env, ctx->handler, ctx->midSetLocator, locator);
    (*env)->DeleteLocalRef(env, locator);
}

void prepareSAXImpl(JNIEnv *env, SContext *ctx, jobject jhandler, xmlSAXHandler *handler) {
    // TODO: handler's method can be cached instead of lookup every time.
    jclass classHandler = (*env)->GetObjectClass(env, jhandler);
    memset(ctx, 0, sizeof(SContext));
    ctx->env = env;
    ctx->handler = jhandler;
    ctx->midStartDocument = (*env)->GetMethodID(env, classHandler, "fireStartDocument", "()V");
    assert(ctx->midStartDocument);
    ctx->midEndDocument = (*env)->GetMethodID(env, classHandler, "fireEndDocument", "()V");
    assert(ctx->midEndDocument);
    ctx->midCharacters = (*env)->GetMethodID(env, classHandler, "fireCharacters", "()V");
    assert(ctx->midCharacters);
    ctx->midIgnorableWhitespace = (*env)->GetMethodID(env, classHandler, "fireIgnorableWhitespace", "()V");
    assert(ctx->midIgnorableWhitespace);
    ctx->midEnsureChars = (*env)->GetMethodID(env, classHandler, "ensureCharacterBufferSize", "(I)[B");
    assert(ctx->midEnsureChars);
    ctx->midStartElement = (*env)->GetMethodID(env, classHandler, "fireStartElement", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;I)V");
    assert(ctx->midStartElement);
    ctx->midEndElement = (*env)->GetMethodID(env, classHandler, "fireEndElement", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    assert(ctx->midEndElement);
    ctx->midProcessingInstruction = (*env)->GetMethodID(env, classHandler, "fireProcessingInstruction", "(Ljava/lang/String;Ljava/lang/String;)V");
    assert(ctx->midProcessingInstruction);
    ctx->midNotationDecl = (*env)->GetMethodID(env, classHandler, "fireNotationDecl", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    assert(ctx->midNotationDecl);
    ctx->midUnparsedEntityDecl = (*env)->GetMethodID(env, classHandler, "fireUnparsedEntityDecl", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    assert(ctx->midUnparsedEntityDecl);
    ctx->midWarning = (*env)->GetMethodID(env, classHandler, "fireWarning", "(Ljava/lang/String;)V");
    assert(ctx->midWarning);
    ctx->midError = (*env)->GetMethodID(env, classHandler, "fireError", "(Ljava/lang/String;)V");
    assert(ctx->midError);
    ctx->midFatalError = (*env)->GetMethodID(env, classHandler, "fireFatalError", "(Ljava/lang/String;)V");
    assert(ctx->midFatalError);
    ctx->midSetLocator = (*env)->GetMethodID(env, classHandler, "fireSetLocator", "(Lrath/libxml/impl/LocatorImpl;)V");
    assert(ctx->midSetLocator);
    
    memset(handler, 0, sizeof(xmlSAXHandler));
    handler->initialized = XML_SAX2_MAGIC;
    handler->startDocument = _startDocument;
    handler->endDocument = _endDocument;
    handler->characters = _characters;
    handler->ignorableWhitespace = _ignorableWhitespace;
    handler->startElementNs = _startElementNs;
    //    handler->startElement = _startElement;
    handler->endElementNs = _endElementNs;
    handler->processingInstruction = _processingInstruction;
    handler->notationDecl = _notationDecl;
    handler->unparsedEntityDecl = _unparsedEntityDecl;
    handler->warning = _warning;
    handler->error = _error;
    handler->fatalError = _fatalError;
    handler->setDocumentLocator = _setDocumentLocator;
    
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseSAXImpl
 * Signature: (Ljava/lang/String;Lrath/libxml/SAXHandler;I)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_LibXml_parseSAXImpl
(JNIEnv *env, jclass clz, jstring jstr, jobject jhandler, jint recovery) {
    SContext ctx;
    xmlSAXHandler handler;
    
    prepareSAXImpl(env, &ctx, jhandler, &handler);

    xmlResetLastError();
    const char *data = (*env)->GetStringUTFChars(env, jstr, NULL);
    jsize data_len = (*env)->GetStringUTFLength(env, jstr);
    xmlDocPtr doc = xmlSAXParseMemoryWithData(&handler, data, data_len, recovery, &ctx);
    (*env)->ReleaseStringUTFChars(env, jstr, data);
    
    if(ctx.locatorContext!=NULL)
        free(ctx.locatorContext);
    if(doc==NULL) {
        throwInternalErrorWithLastError(env);
        return;
    }
    xmlFreeDoc(doc);
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseSAXFileImpl
 * Signature: (Ljava/lang/String;Lrath/libxml/impl/SAXHandlerEngine;I)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_LibXml_parseSAXFileImpl
(JNIEnv *env, jclass cls, jstring jpath, jobject jhandler, jint recovery) {
    SContext ctx;
    xmlSAXHandler handler;
    
    prepareSAXImpl(env, &ctx, jhandler, &handler);

    xmlResetLastError();
    const char *path = (*env)->GetStringUTFChars(env, jpath, NULL);
    xmlDocPtr doc = xmlSAXParseFileWithData(&handler, path, recovery, &ctx);
    (*env)->ReleaseStringUTFChars(env, jpath, path);
    
    if(ctx.locatorContext!=NULL)
        free(ctx.locatorContext);
    if(doc==NULL) {
        throwInternalErrorWithLastError(env);
        return;
    }
    xmlFreeDoc(doc);
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseSAXSystemIdImpl
 * Signature: (Ljava/lang/String;Ljava/io/InputStream;Lrath/libxml/impl/SAXHandlerEngine;I)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_LibXml_parseSAXSystemIdImpl
(JNIEnv *env, jclass cls, jstring jSystemId, jobject inputStream, jobject jhandler, jint recovery) {
    SContext ctx;
    xmlSAXHandler handler;
    char chunk[CHUNK_SIZE];
    
    prepareSAXImpl(env, &ctx, jhandler, &handler);
    
    xmlResetLastError();
    const char *systemId = (*env)->GetStringUTFChars(env, jSystemId, NULL);
    
    xmlParserCtxt *parser = xmlCreatePushParserCtxt(&handler, 0, NULL, 0, systemId);
    parser->_private = &ctx;
   
    int ret;
    int readlen;
    jbyteArray buf = (*env)->NewByteArray(env, CHUNK_SIZE);
    while(1) {
        readlen = (*env)->CallIntMethod(env, inputStream, methodInputStreamRead, buf, 0, CHUNK_SIZE);
        if( readlen==-1 || (*env)->ExceptionOccurred(env))
            break;
        (*env)->GetByteArrayRegion(env, buf, 0, readlen, (jbyte*)chunk);
        ret = xmlParseChunk(parser, chunk, readlen, 0);
        assert(!ret);
    }
    if(!(*env)->ExceptionOccurred(env))
        xmlParseChunk(parser, chunk, 0, 1);
    (*env)->DeleteLocalRef(env, buf);
    (*env)->ReleaseStringUTFChars(env, jSystemId, systemId);
    
    // if(!parser->wellFormed) then something went wrong...
    
    xmlFreeParserCtxt(parser);
    
    if(ctx.locatorContext!=NULL)
        free(ctx.locatorContext);
}
#include "org_xmlsoft_Document.h"
#include <libxml/parser.h>
#include "cache.h"
#include "utils.h"
#include <assert.h>

/*
 * Class:     org_xmlsoft_Document
 * Method:    getRootElementImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Document_getRootElementImpl
  (JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    xmlNode *rootNode = xmlDocGetRootElement(doc);
    return buildNode(env, rootNode, obj);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    setVersionImpl
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Document_setVersionImpl
(JNIEnv *env, jobject obj, jstring jVersion) {
    xmlDoc *doc = findDocument(env, obj);
    xmlFree((void*)doc->version);
    const char *version = (*env)->GetStringUTFChars(env, jVersion, NULL);
    doc->version = xmlStrdup((xmlChar*)version);
    (*env)->ReleaseStringUTFChars(env, jVersion, version);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    getVersionImpl
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_Document_getVersionImpl
(JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    return (*env)->NewStringUTF(env, (const char*)doc->version);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Document_disposeImpl
  (JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    xmlFreeDoc(doc);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    createXPathContextImpl
 * Signature: ()Lrath/libxml/XPathContext;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Document_createXPathContextImpl
  (JNIEnv *env, jobject obj) {
    xmlDoc *doc = findDocument(env, obj);
    xmlXPathContext *ctx = xmlXPathNewContext(doc);
    return buildXPathContext(env, ctx);
}


/*
 * Class:     org_xmlsoft_Document
 * Method:    createElementImpl
 * Signature: (Lrath/libxml/Namespace;Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Document_createElementImpl
(JNIEnv *env, jobject obj, jobject jnamespace, jstring jname) {
    xmlDoc *doc = findDocument(env, obj);
    xmlNode *created;
    const char *name = (*env)->GetStringUTFChars(env, jname, NULL);
    created = xmlNewDocNode(doc, NULL, (xmlChar*)name, NULL);
    if (jnamespace!=NULL) {
        jstring jhref = (*env)->CallNonvirtualObjectMethod(env, jnamespace, classNamespace, methodNamespaceGetHref);
        jstring jprefix = (*env)->CallNonvirtualObjectMethod(env, jnamespace, classNamespace, methodNamespaceGetPrefix);
        const char *href = (*env)->GetStringUTFChars(env, jhref, NULL);
        const char *prefix = NULL;
        if( jprefix!=NULL ) {
            prefix = (*env)->GetStringUTFChars(env, jprefix, NULL);
        }
        xmlNs *ns = xmlNewNs(created, (xmlChar*)href, (xmlChar*)prefix);
        // TODO: free ns?
        if( prefix!=NULL ) {
            (*env)->ReleaseStringUTFChars(env, jprefix, prefix);
        }
        (*env)->ReleaseStringUTFChars(env, jhref, href);
    }
    (*env)->ReleaseStringUTFChars(env, jname, name);
    return buildNode(env, created, obj);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    createTextImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Document_createTextImpl
(JNIEnv *env, jobject obj, jstring jstr) {
    xmlDoc *doc = findDocument(env, obj);
    
    const char *str = (*env)->GetStringUTFChars(env, jstr, NULL);
    jsize len = (*env)->GetStringUTFLength(env, jstr);
    xmlNode *textNode = xmlNewDocTextLen(doc, (xmlChar*)str, len);
    (*env)->ReleaseStringUTFChars(env, jstr, str);
    
    return buildNode(env, textNode, obj);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    createCommentImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Document_createCommentImpl
(JNIEnv *env, jobject obj, jstring jstr) {
//    xmlDoc *doc = findDocument(env, obj);
    
    const char *str = (*env)->GetStringUTFChars(env, jstr, NULL);
    xmlNode *commentNode = xmlNewComment((xmlChar*)str);
    (*env)->ReleaseStringUTFChars(env, jstr, str);
    
    return buildNode(env, commentNode, obj);
}


/*
 * Class:     org_xmlsoft_Document
 * Method:    createCDataImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Document_createCDataImpl
(JNIEnv *env, jobject obj, jstring jstr) {
    xmlDoc *doc = findDocument(env, obj);
    
    const char *str = (*env)->GetStringUTFChars(env, jstr, NULL);
    jsize len = (*env)->GetStringUTFLength(env, jstr);
    xmlNode *cdata = xmlNewCDataBlock(doc, (xmlChar*)str, len);
    (*env)->ReleaseStringUTFChars(env, jstr, str);
    
    return buildNode(env, cdata, obj);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    createPIImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Document_createPIImpl
(JNIEnv *env, jobject obj, jstring jname, jstring jcontent) {
    xmlDocPtr doc = findDocument(env, obj);
    const char *name = (*env)->GetStringUTFChars(env, jname, NULL);
    const char *content = (*env)->GetStringUTFChars(env, jcontent, NULL);
    
    xmlNodePtr node = xmlNewDocPI(doc, (const xmlChar*)name, (const xmlChar*)content);
    
    (*env)->ReleaseStringUTFChars(env, jname, name);
    (*env)->ReleaseStringUTFChars(env, jcontent, content);
    
    return buildNode(env, node, obj);
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    createDocumentImpl
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_org_xmlsoft_Document_createDocumentImpl
(JNIEnv *env, jclass clz, jstring jVersion) {
    const char *version = (*env)->GetStringUTFChars(env, jVersion, NULL);
    xmlDocPtr doc = xmlNewDoc((const xmlChar*)version);
    (*env)->ReleaseStringUTFChars(env, jVersion, version);
    return (jlong)doc;
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    saveImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Document_saveImpl
(JNIEnv *env, jobject obj, jstring jpath, jstring jencoding) {
    xmlDoc *doc = findDocument(env, obj);
    const char *path = (*env)->GetStringUTFChars(env, jpath, NULL);
    const char *encoding = (*env)->GetStringUTFChars(env, jencoding, NULL);
    
    int ret = xmlSaveFileEnc(path, doc, encoding);
    
    (*env)->ReleaseStringUTFChars(env, jpath, path);
    (*env)->ReleaseStringUTFChars(env, jencoding, encoding);
    
    if (ret < 1) {
        throwInternalErrorWithLastError(env);
    }
}

typedef struct {
    JNIEnv *env;
    jobject outputStream;
    jsize bufferlen;
    jbyteArray buffer;
} IOCallbackContext;

static int writeCallback(void *p, const char *buffer, int len) {
    IOCallbackContext *ctx = (IOCallbackContext*)p;
    JNIEnv *env = ctx->env;
    
    int remain = len;
    int copying;
    while(1) {
        copying = ctx->bufferlen < remain ? ctx->bufferlen : remain;
        (*env)->SetByteArrayRegion(env, ctx->buffer, 0, copying, (const jbyte*)buffer);
        (*env)->CallVoidMethod(env, ctx->outputStream, methodOutputStreamWrite, ctx->buffer, 0, copying);
        remain -= copying;
        buffer += copying;
        if (remain < 1)
            break;
    }
    return len;
}

static int closeCallback(void *ctx) {
    // do nothing please
    return 0;
}

/*
 * Class:     org_xmlsoft_Document
 * Method:    saveStreamImpl
 * Signature: (Ljava/io/OutputStream;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Document_saveStreamImpl
(JNIEnv *env, jobject obj, jobject outputStream, jstring jencoding) {
    int ret;
    IOCallbackContext context;
    context.env = env;
    context.outputStream = outputStream;
    context.bufferlen = 64;
    context.buffer = (*env)->NewByteArray(env, context.bufferlen);
    
    xmlDoc *doc = findDocument(env, obj);
    xmlCharEncodingHandler *handler = xmlFindCharEncodingHandler("utf8");
    assert(handler);
    const char *encoding = (*env)->GetStringUTFChars(env, jencoding, NULL);
    xmlOutputBuffer *buffer = xmlOutputBufferCreateIO(writeCallback, closeCallback, &context, handler);
    ret = xmlSaveFileTo(buffer, doc, encoding);
    if(ret==-1) {
        throwInternalErrorWithLastError(env);
    }
    (*env)->ReleaseStringUTFChars(env, jencoding, encoding);
    (*env)->DeleteLocalRef(env, context.buffer);
}

typedef struct {
    JNIEnv *env;
    jobject writer;
    jobject charset;
    jsize bufferlen;
    jbyteArray buffer;
    jcharArray bufferc;
} IOCallbackWriterContext;

static int writerCallback(void *p, const char *buffer, int len) {
    IOCallbackWriterContext *ctx = (IOCallbackWriterContext*)p;
    JNIEnv *env = ctx->env;
    
    int remain = len;
    int copying;
    while(1) {
        copying = ctx->bufferlen < remain ? ctx->bufferlen : remain;
        (*env)->SetByteArrayRegion(env, ctx->buffer, 0, copying, (const jbyte*)buffer);
        
        jobject byteBuffer = (*env)->CallStaticObjectMethod(env, classByteBuffer, methodByteBufferWrap, ctx->buffer, 0, copying);
        jobject charBuffer = (*env)->CallObjectMethod(env, ctx->charset, methodCharsetDecode, byteBuffer);
        
        jint charLength = (*env)->CallIntMethod(env, charBuffer, methodCharBufferLength);
        jobject charBuffer_  = (*env)->CallObjectMethod(env, charBuffer, methodCharBufferGet, ctx->bufferc, 0, charLength);
        (*env)->CallVoidMethod(env, ctx->writer, methodWriterWrite, ctx->bufferc, 0, charLength);
        
        (*env)->DeleteLocalRef(env, charBuffer);
        (*env)->DeleteLocalRef(env, byteBuffer);
        (*env)->DeleteLocalRef(env, charBuffer_);
        remain -= copying;
        buffer += copying;
        if (remain < 1)
            break;
    }
    return len;
}


/*
 * Class:     org_xmlsoft_Document
 * Method:    saveWriterImpl
 * Signature: (Ljava/io/Writer;Ljava/lang/String;Ljava/nio/charset/Charset;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Document_saveWriterImpl
(JNIEnv *env, jobject obj, jobject writer, jstring jencoding, jobject charset) {
    int ret;
    IOCallbackWriterContext context;
    context.env = env;
    context.writer = writer;
    context.charset = charset;
    context.bufferlen = 512;
    context.buffer = (*env)->NewByteArray(env, context.bufferlen);
    context.bufferc = (*env)->NewCharArray(env, context.bufferlen);
    
    xmlDoc *doc = findDocument(env, obj);
    xmlCharEncodingHandler *handler = xmlFindCharEncodingHandler("utf8");
    assert(handler);
    const char *encoding = (*env)->GetStringUTFChars(env, jencoding, NULL);
    xmlOutputBuffer *buffer = xmlOutputBufferCreateIO(writerCallback, closeCallback, &context, handler);
    ret = xmlSaveFileTo(buffer, doc, encoding);
    if(ret==-1) {
        throwInternalErrorWithLastError(env);
    }
    (*env)->ReleaseStringUTFChars(env, jencoding, encoding);
    (*env)->DeleteLocalRef(env, context.buffer);
}

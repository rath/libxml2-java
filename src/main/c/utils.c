#include "utils.h"
#include <assert.h>

jlong       findBaseNode(JNIEnv *env, jobject obj);

jobject     buildNode(JNIEnv *env, xmlNode *node, jobject documentHolder) {
    jlong pNode = (jlong)node;
    jobject jnode = createPointerObject(env, "rath/libxml/Node", pNode);

    jclass cz = (*env)->GetObjectClass(env, documentHolder);
    jmethodID methodGetDocument = (*env)->GetMethodID(env, cz, "getDocument", "()Lrath/libxml/Document;");
    jobject jdocument = (*env)->CallObjectMethod(env, documentHolder, methodGetDocument);
    jmethodID methodSetDocument = (*env)->GetMethodID(env, (*env)->GetObjectClass(env, jnode), "setDocument", "(Lrath/libxml/Document;)V");
    (*env)->CallVoidMethod(env, jnode, methodSetDocument, jdocument);
    (*env)->DeleteLocalRef(env, jdocument);
    return jnode;
}

jobject     buildDocument(JNIEnv *env, xmlDoc *doc) {
    return createPointerObject(env, "rath/libxml/Document", (jlong)doc);
}

jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject documentHolder) {
    jclass cl = (*env)->FindClass(env, "rath/libxml/NodeSet");
    jmethodID constructorId = (*env)->GetMethodID(env, cl, "<init>", "(J)V");
    jobject jnodeset = (*env)->NewObject(env, cl, constructorId, (jlong)nodeset);

    if( nodeset==NULL )
        return jnodeset;

    jfieldID fid = (*env)->GetFieldID(env, cl, "size", "I");
    (*env)->SetIntField(env, jnodeset, fid, (jint)nodeset->nodeNr);

    if(!xmlXPathNodeSetIsEmpty(nodeset)) {
        jmethodID mid = (*env)->GetMethodID(env, cl, "addNode", "(Lrath/libxml/Node;)V");
        for(int i=0; i<nodeset->nodeNr; i++) {
            (*env)->CallVoidMethod(env, jnodeset, mid, buildNode(env, nodeset->nodeTab[i], documentHolder));
        }
    }
    return jnodeset;
}

xmlDocPtr   findDocument(JNIEnv *env, jobject obj) {
    return (xmlDocPtr)findBaseNode(env, obj);
}

xmlNodePtr  findNode(JNIEnv *env, jobject obj) {
    return (xmlNodePtr)findBaseNode(env, obj);
}

xmlXPathContextPtr  findXPathContext(JNIEnv *env, jobject obj) {
    return (xmlXPathContextPtr)findBaseNode(env, obj);
}

jlong       findBaseNode(JNIEnv *env, jobject obj) {
    jfieldID fieldP = (*env)->GetFieldID(env, (*env)->GetObjectClass(env, obj), "p", "J");
    jlong pThis = (*env)->GetLongField(env, obj, fieldP);
    return pThis;
}

jobject     buildXPathContext(JNIEnv *env, xmlXPathContext *ctx) {
    jobject jctx = createPointerObject(env, "rath/libxml/XPathContext", (jlong)ctx);
    return jctx;
}

jobject     createPointerObject(JNIEnv *env, const char *className, jlong pointer) {
    jclass cl = (*env)->FindClass(env, className);
    jmethodID constructorId = (*env)->GetMethodID(env, cl, "<init>", "(J)V");
    return (*env)->NewObject(env, cl, constructorId, pointer);
}

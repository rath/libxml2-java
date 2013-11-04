#include "utils.h"
#include <assert.h>

jlong       findBaseNode(JNIEnv *env, jobject obj);

jobject     buildNode(JNIEnv *env, xmlNode *node, jobject documentHolder) {
    jlong pNode = (jlong)node;
    jobject jnode = createPointerObject(env, "rath/libxml/Node", pNode);

    jclass cz = env->GetObjectClass(documentHolder);
    jmethodID methodGetDocument = env->GetMethodID(cz, "getDocument", "()Lrath/libxml/Document;");
    jobject jdocument = env->CallObjectMethod(documentHolder, methodGetDocument);
    jmethodID methodSetDocument = env->GetMethodID(cz, "setDocument", "(Lrath/libxml/Document;)V");
    env->CallVoidMethod(jnode, methodSetDocument, jdocument);

    return jnode;
}

jobject     buildDocument(JNIEnv *env, xmlDoc *doc) {
    return createPointerObject(env, "rath/libxml/Document", (jlong)doc);
}

jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject documentHolder) {
    jclass cl = env->FindClass("rath/libxml/NodeSet");
    jmethodID constructorId = env->GetMethodID(cl, "<init>", "(J)V");
    jobject jnodeset = env->NewObject(cl, constructorId, (jlong)nodeset);

    if( nodeset==NULL )
        return jnodeset;

    jfieldID fid = env->GetFieldID(cl, "size", "I");
assert(fid!=0);
    env->SetIntField(jnodeset, fid, (jint)nodeset->nodeNr);

    if(!xmlXPathNodeSetIsEmpty(nodeset)) {
        jmethodID mid = env->GetMethodID(cl, "addNode", "(Lrath/libxml/Node;)V");
assert(mid!=0);
        for(int i=0; i<nodeset->nodeNr; i++) {
            env->CallVoidMethod(jnodeset, mid, buildNode(env, nodeset->nodeTab[i], documentHolder));
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
    jfieldID fieldP = env->GetFieldID(env->GetObjectClass(obj), "p", "J");
    jlong pThis = env->GetLongField(obj, fieldP);
    return pThis;
}

jobject     buildXPathContext(JNIEnv *env, xmlXPathContext *ctx) {
    jobject jctx = createPointerObject(env, "rath/libxml/XPathContext", (jlong)ctx);
    return jctx;
}

jobject     createPointerObject(JNIEnv *env, const char *className, jlong pointer) {
    jclass cl = env->FindClass(className);
    jmethodID constructorId = env->GetMethodID(cl, "<init>", "(J)V");
    return env->NewObject(cl, constructorId, pointer);
}

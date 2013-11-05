#include "utils.h"
#include <assert.h>
#include "cache.h"

jobject     buildNode(JNIEnv *env, xmlNode *node, jobject documentHolder) {
    jobject jnode = (*env)->NewObject(env, classNode, methodNodeNew, (jlong)node);

    jclass cz = (*env)->GetObjectClass(env, documentHolder);
    jmethodID methodGetDocument = (*env)->GetMethodID(env, cz, "getDocument", "()Lrath/libxml/Document;");
    jobject jdocument = (*env)->CallObjectMethod(env, documentHolder, methodGetDocument);
    
    (*env)->CallVoidMethod(env, jnode, methodNodeSetDocument, jdocument);
    (*env)->DeleteLocalRef(env, jdocument);
    return jnode;
}

jobject     buildDocument(JNIEnv *env, xmlDoc *doc) {
    return (*env)->NewObject(env, classDocument, methodDocumentNew, (jlong)doc);
}

jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject documentHolder) {
    jobject jnodeset = (*env)->NewObject(env, classNodeset, methodNodesetNew, (jlong)nodeset);

    if( nodeset==NULL )
        return jnodeset;

    jfieldID fid = (*env)->GetFieldID(env, classNodeset, "size", "I");
    (*env)->SetIntField(env, jnodeset, fid, (jint)nodeset->nodeNr);

    if(!xmlXPathNodeSetIsEmpty(nodeset)) {
        jmethodID mid = (*env)->GetMethodID(env, classNodeset, "addNode", "(Lrath/libxml/Node;)V");
        for(int i=0; i<nodeset->nodeNr; i++) {
            (*env)->CallVoidMethod(env, jnodeset, mid, buildNode(env, nodeset->nodeTab[i], documentHolder));
        }
    }
    return jnodeset;
}

xmlDocPtr   findDocument(JNIEnv *env, jobject obj) {
    return (xmlDocPtr)(*env)->GetLongField(env, obj, fieldDocumentGetP);
}

xmlNodePtr  findNode(JNIEnv *env, jobject obj) {
    return (xmlNodePtr)(*env)->GetLongField(env, obj, fieldNodeGetP);
}

xmlXPathContextPtr  findXPathContext(JNIEnv *env, jobject obj) {
    return (xmlXPathContextPtr)(*env)->GetLongField(env, obj, fieldXPathContextGetP);
}

jobject     buildXPathContext(JNIEnv *env, xmlXPathContext *ctx) {
    jobject jctx = (*env)->NewObject(env, classXPathContext, methodXPathContextNew, (jlong)ctx);
    return jctx;
}


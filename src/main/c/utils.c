#include "utils.h"
#include "cache.h"
#include <assert.h>

jobject     buildNode(JNIEnv *env, xmlNode *node, jobject document) {
    jobject jnode = (*env)->NewObject(env, classNode, methodNodeNew, (jlong)node);
    (*env)->CallVoidMethod(env, jnode, methodNodeSetDocument, document);
    return jnode;
}

jobject     buildDocument(JNIEnv *env, xmlDoc *doc) {
    return (*env)->NewObject(env, classDocument, methodDocumentNew, (jlong)doc);
}

jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject document) {
    jobject jnodeset = (*env)->NewObject(env, classNodeset, methodNodesetNew, (jlong)nodeset);

    if( nodeset==NULL )
        return jnodeset;

    jfieldID fid = (*env)->GetFieldID(env, classNodeset, "size", "I");
    (*env)->SetIntField(env, jnodeset, fid, (jint)nodeset->nodeNr);

    if(!xmlXPathNodeSetIsEmpty(nodeset)) {
        jmethodID mid = (*env)->GetMethodID(env, classNodeset, "addNode", "(Lrath/libxml/Node;)V");
        for(int i=0; i<nodeset->nodeNr; i++) {
            (*env)->CallVoidMethod(env, jnodeset, mid, buildNode(env, nodeset->nodeTab[i], document));
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


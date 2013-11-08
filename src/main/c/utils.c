#include "utils.h"
#include "cache.h"
#include <assert.h>

jobject     buildNode(JNIEnv *env, xmlNode *node, jobject document) {
    if( node==NULL )
        return NULL;
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

    (*env)->SetIntField(env, jnodeset, fieldNodesetSize, (jint)nodeset->nodeNr);

    if(!xmlXPathNodeSetIsEmpty(nodeset)) {
        for(int i=0; i<nodeset->nodeNr; i++) {
            (*env)->CallVoidMethod(env, jnodeset, methodNodesetAddNode, buildNode(env, nodeset->nodeTab[i], document));
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

xmlXPathObjectPtr   findXPathObject(JNIEnv *env, jobject obj) {
    return (xmlXPathObjectPtr)(*env)->GetLongField(env, obj, fieldXPathObjectGetP);
}

jobject     buildXPathContext(JNIEnv *env, xmlXPathContext *ctx) {
    jobject jctx = (*env)->NewObject(env, classXPathContext, methodXPathContextNew, (jlong)ctx);
    return jctx;
}

int throwInternalErrorWithLastError(JNIEnv *env) {
    xmlError *error = xmlGetLastError(); // TODO: are you sure that is safe on multiple threads?
    if (error==NULL) {
        return 0;
    }
    jthrowable e = (jthrowable)(*env)->NewObject(env, classError, methodErrorNew, error->code,
        (*env)->NewStringUTF(env, error->message), error->line, error->int2);
    (*env)->Throw(env, e);
    return 1;
}

void throwInternalErrorWithMessage(JNIEnv *env, const char* msg) {
    (*env)->ThrowNew(env, classError, msg);
}
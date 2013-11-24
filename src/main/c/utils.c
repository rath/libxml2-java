#include "utils.h"
#include "cache.h"
#include <assert.h>
#include <stdio.h>
#include <time.h>
#ifdef __MACH__
#include <mach/clock.h>
#include <mach/mach.h>
#endif

jobject     buildNode(JNIEnv *env, xmlNode *node, jobject document) {
    if( node==NULL )
        return NULL;
    return (*env)->NewObject(env, classNode, methodNodeNewWithArgs, (jlong)node, node->type, document);
}

jobject     buildDocument(JNIEnv *env, xmlDoc *doc) {
    return (*env)->NewObject(env, classDocument, methodDocumentNew, (jlong)doc);
}

jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject document) {
    int i;
    jobject jnodeset = (*env)->NewObject(env, classNodeset, methodNodesetNew, (jlong)nodeset);
    if( nodeset==NULL )
        return jnodeset;

    (*env)->SetIntField(env, jnodeset, fieldNodesetSize, (jint)nodeset->nodeNr);

    if(!xmlXPathNodeSetIsEmpty(nodeset)) {
        for(i=0; i<nodeset->nodeNr; i++) {
            jobject node = buildNode(env, nodeset->nodeTab[i], document);
            (*env)->CallNonvirtualVoidMethod(env, jnodeset, classNodeset, methodNodesetAddNode, node);
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

LocatorContext*     findLocator(JNIEnv *env, jobject obj) {
    return (LocatorContext*)(*env)->GetLongField(env, obj, fieldLocatorP);
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

void get_nanotime(struct timespec *buf) {
#ifdef __MACH__
    clock_serv_t cclock;
    mach_timespec_t mts;
    host_get_clock_service(mach_host_self(), CALENDAR_CLOCK, &cclock);
    clock_get_time(cclock, &mts);
    mach_port_deallocate(mach_task_self(), cclock);
    buf->tv_sec = mts.tv_sec;
    buf->tv_nsec = mts.tv_nsec;
#else
    clock_gettime(CLOCK_REALTIME, buf);
#endif
}

long profile_start() {
    struct timespec t;
    get_nanotime(&t);
    return t.tv_nsec;
}

void profile_end(const char *title, long start_time) {
    struct timespec t;
    get_nanotime(&t);
    long end_time = t.tv_nsec;
    if( end_time < start_time ) {
        end_time += 1000000000L;
    }
    fprintf(stdout, "%s: %ld ns\n", title, (end_time-start_time));
}
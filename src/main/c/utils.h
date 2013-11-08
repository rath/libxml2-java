#include <jni.h>
#include <libxml/parser.h>
#include <libxml/xpath.h>

xmlDocPtr   findDocument(JNIEnv *env, jobject obj);
xmlNodePtr  findNode(JNIEnv *env, jobject obj);
xmlXPathContextPtr  findXPathContext(JNIEnv *env, jobject obj);
xmlXPathObjectPtr   findXPathObject(JNIEnv *env, jobject obj);
jobject     buildDocument(JNIEnv *env, xmlDoc *doc);
jobject     buildNode(JNIEnv *env, xmlNode *node, jobject documentHolder);
jobject     buildXPathContext(JNIEnv *env, xmlXPathContext *ctx);
jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject document);

/*
 * returns 1 - succeed and thrown error, 0 - xmlGetLastError returns null
 */
int throwInternalErrorWithLastError(JNIEnv *env);
void throwInternalErrorWithMessage(JNIEnv *env, const char* msg);
#include <jni.h>
#include <libxml/parser.h>
#include <libxml/xpath.h>

xmlDocPtr   findDocument(JNIEnv *env, jobject obj);
xmlNodePtr  findNode(JNIEnv *env, jobject obj);
xmlXPathContextPtr  findXPathContext(JNIEnv *env, jobject obj);
jobject     buildDocument(JNIEnv *env, xmlDoc *doc);
jobject     buildNode(JNIEnv *env, xmlNode *node, jobject documentHolder);
jobject     buildXPathContext(JNIEnv *env, xmlXPathContext *ctx);
jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject documentHolder);
jobject     createPointerObject(JNIEnv *env, const char *className, jlong pointer);

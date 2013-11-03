#include <jni.h>
#include <libxml/parser.h>

xmlDocPtr   findDocument(JNIEnv *env, jobject obj);
xmlNodePtr  findNode(JNIEnv *env, jobject obj);
jobject     buildDocument(JNIEnv *env, xmlDoc *doc);
jobject     buildNode(JNIEnv *env, xmlNode *node);

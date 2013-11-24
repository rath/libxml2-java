#include <jni.h>
#include <libxml/parser.h>
#include <libxml/xpath.h>

typedef struct {
    xmlParserCtxt *parser;
    xmlSAXLocator *locator;
} LocatorContext;

xmlDocPtr   findDocument(JNIEnv *env, jobject obj);
xmlNodePtr  findNode(JNIEnv *env, jobject obj);
xmlXPathContextPtr  findXPathContext(JNIEnv *env, jobject obj);
xmlXPathObjectPtr   findXPathObject(JNIEnv *env, jobject obj);
LocatorContext*     findLocator(JNIEnv *env, jobject obj);

jobject     buildDocument(JNIEnv *env, xmlDoc *doc);
jobject     buildNode(JNIEnv *env, xmlNode *node, jobject document);
jobject     buildXPathContext(JNIEnv *env, xmlXPathContext *ctx);
jobject     buildNodeSet(JNIEnv *env, xmlNodeSet *nodeset, jobject document);

/*
 * returns 1 - succeed and thrown error, 0 - xmlGetLastError returns null
 */
int throwInternalErrorWithLastError(JNIEnv *env);
void throwInternalErrorWithMessage(JNIEnv *env, const char* msg);

long profile_start();
void profile_end(const char *title, long start_time);
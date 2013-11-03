#include "rath_libxml_LibXml.h"
#include <libxml/parser.h>

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseFileImpl
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_rath_libxml_LibXml_parseFileImpl
  (JNIEnv *env, jclass clazz, jstring filepath) {
  const char *path = env->GetStringUTFChars(filepath, NULL);
  xmlDoc *doc = xmlParseFile(path);
  env->ReleaseStringUTFChars(filepath, path);
  return (jlong)doc;
}

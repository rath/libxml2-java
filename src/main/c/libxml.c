#include "rath_libxml_LibXml.h"
#include <libxml/parser.h>
#include <string.h>
#include "utils.h"

/*
 * Class:     rath_libxml_LibXml
 * Method:    initInternalParser
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_LibXml_initInternalParser
  (JNIEnv *env, jclass clz) {
    xmlInitParser();
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseFileImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Document;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_LibXml_parseFileImpl
  (JNIEnv *env, jclass clazz, jstring filepath) {
    const char *path = (*env)->GetStringUTFChars(env, filepath, NULL);
    xmlDoc *doc = xmlParseFile(path);
    (*env)->ReleaseStringUTFChars(env, filepath, path);
    return buildDocument(env, doc);
}

/*
 * Class:     rath_libxml_LibXml
 * Method:    parseStringImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Document;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_LibXml_parseStringImpl
  (JNIEnv *env, jclass clazz, jstring jdata) {
    const char *data = (*env)->GetStringUTFChars(env, jdata, NULL);
    size_t datalen = strlen(data);
    xmlDoc *doc = xmlReadMemory(data, (int)datalen, "in_memory.xml", "UTF8", 0); // TODO: Handling xmlParserOption
    (*env)->ReleaseStringUTFChars(env, jdata, data);
    return buildDocument(env, doc);
}
#include "rath_libxml_LibXml.h"
#include <libxml/parser.h>
#include <string.h>
#include "utils.h"

jclass classDocument;
jclass classNode;
jclass classNodeSet;
jclass classNamespace;
jclass classXPathContext;
jclass classXPathObject;

jmethodID methodDocumentNew;
jmethodID methodNodeNew;
jmethodID methodNodesetNew;
jmethodID methodNamespaceNew;
jmethodID methodXPathContextNew;
jmethodID methodXPathObjectNew;

jmethodID methodNodeSetType;
jmethodID methodNodeSetDocument;

jmethodID methodListAdd;

jfieldID fieldDocumentGetP;
jfieldID fieldNodeGetP;
jfieldID fieldXPathContextGetP;
jfieldID fieldNodeSetName;
jfieldID fieldNodeSetNamespace;
jfieldID fieldXPathObjectSetNodeset;
jfieldID fieldXPathObjectSetBool;
jfieldID fieldXPathObjectSetFloat;
jfieldID fieldXPathObjectSetString;

/*
 * Class:     rath_libxml_LibXml
 * Method:    initInternalParser
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_LibXml_initInternalParser
(JNIEnv *env, jclass clz) {
    xmlInitParser();
    
    classDocument = (*env)->FindClass(env, "rath/libxml/Document");
    classNode = (*env)->FindClass(env, "rath/libxml/Node");
    classNodeSet = (*env)->FindClass(env, "rath/libxml/NodeSet");
    classNamespace = (*env)->FindClass(env, "rath/libxml/Namespace");
    classXPathContext = (*env)->FindClass(env, "rath/libxml/XPathContext");
    classXPathObject = (*env)->FindClass(env, "rath/libxml/XPathObject");
    
    methodDocumentNew = (*env)->GetMethodID(env, classDocument, "<init>", "(J)V");
    methodNodeNew = (*env)->GetMethodID(env, classNode, "<init>", "(J)V");
    methodNodesetNew = (*env)->GetMethodID(env, classNodeSet, "<init>", "(J)V");
    methodNamespaceNew = (*env)->GetMethodID(env, classNamespace, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
    methodXPathContextNew = (*env)->GetMethodID(env, classXPathContext, "<init>", "(J)V");
    methodXPathObjectNew = (*env)->GetMethodID(env, classXPathObject, "<init>", "(J)V");
    
    methodNodeSetType = (*env)->GetMethodID(env, classNode, "setType", "(I)V");
    methodNodeSetDocument = (*env)->GetMethodID(env, classNode, "setDocument", "(Lrath/libxml/Document;)V");
    
    jclass classList = (*env)->FindClass(env, "java/util/List");
    methodListAdd = (*env)->GetMethodID(env, classList, "add", "(Ljava/lang/Object;)Z");
    
    fieldDocumentGetP = (*env)->GetFieldID(env, classDocument, "p", "J");
    fieldNodeGetP = (*env)->GetFieldID(env, classNode, "p", "J");
    fieldNodeSetName = (*env)->GetFieldID(env, classNode, "name", "Ljava/lang/String;");
    fieldXPathContextGetP = (*env)->GetFieldID(env, classXPathContext, "p", "J");
    fieldNodeSetNamespace = (*env)->GetFieldID(env, classNode, "namespace", "Lrath/libxml/Namespace;");
    fieldXPathObjectSetNodeset = (*env)->GetFieldID(env, classXPathObject, "nodeset", "Lrath/libxml/NodeSet;");
    fieldXPathObjectSetBool = (*env)->GetFieldID(env, classXPathObject, "booleanValue", "Z");
    fieldXPathObjectSetFloat = (*env)->GetFieldID(env, classXPathObject, "floatValue", "D");
    fieldXPathObjectSetString = (*env)->GetFieldID(env, classXPathObject, "stringValue", "Ljava/lang/String;");
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
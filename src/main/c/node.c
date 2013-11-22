#include "org_xmlsoft_Node.h"
#include <libxml/parser.h>
#include "utils.h"
#include <assert.h>
#include "cache.h"

#define DOC(obj) get_document(env, obj)
jobject get_document(JNIEnv *env, jobject node) { return (*env)->GetObjectField(env, node, fieldNodeDocument); }

/*
 * Class:     org_xmlsoft_Node
 * Method:    childrenImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_childrenImpl
(JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    xmlNode *children = node->children;
    return buildNode(env, children, DOC(obj));
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillNamespaceImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillNamespaceImpl
(JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    xmlNs *ns = node->ns;
    if( ns==NULL )
        return;
    
    // Will crash if node finded is xmlDocPtr. Access Violation.
    jstring href = (*env)->NewStringUTF(env, (const char*)ns->href);
    jstring prefix = (*env)->NewStringUTF(env, (const char*)ns->prefix);
    jobject jNs = (*env)->NewObject(env, classNamespace, methodNamespaceNew, href, prefix);
    (*env)->SetObjectField(env, obj, fieldNodeSetNamespace, jNs);
}


/*
 * Class:     org_xmlsoft_Node
 * Method:    fillNameImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillNameImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    jstring nodeName = (*env)->NewStringUTF(env, (const char*)node->name);

    (*env)->SetObjectField(env, obj, fieldNodeSetName, nodeName);
    (*env)->DeleteLocalRef(env, nodeName);
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    nextImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_nextImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    if (node->next==NULL )
        return NULL;
    return buildNode(env, node->next, DOC(obj));
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    hasNext
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_org_xmlsoft_Node_hasNext
  (JNIEnv *env, jobject obj) {
    return findNode(env, obj)->next!=NULL;
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    previousImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_previousImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    if (node->prev==NULL )
        return NULL;
    return buildNode(env, node->prev, DOC(obj));
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    getParentImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_getParentImpl
(JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
//    if( node->parent!=NULL && node->parent->type==XML_DOCUMENT_NODE) {
//    
//    }
    return buildNode(env, node->parent, DOC(obj));
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    getLastImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_getLastImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    return buildNode(env, node->last, DOC(obj));
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    getTextImpl
 * Signature: (Z)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_Node_getTextImpl
  (JNIEnv *env, jobject obj, jboolean format) {
    xmlNode *node = findNode(env, obj);
    const xmlChar *text = xmlNodeListGetString(node->doc, node, format==JNI_TRUE ? 1 : 0);
    jstring jText = (*env)->NewStringUTF(env, (const char*)text);
    xmlFree((void*)text);
    return jText;
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    getPropImpl
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_Node_getPropImpl
  (JNIEnv *env, jobject obj, jstring jprop) {
    xmlNode *node = findNode(env, obj);

    xmlChar *prop = (xmlChar*)(*env)->GetStringUTFChars(env, jprop, NULL);
    xmlChar *value = xmlGetProp(node, prop);
    (*env)->ReleaseStringUTFChars(env, jprop, (const char*)prop);
    if (value==NULL)
        return NULL;

    jstring jvalue = (*env)->NewStringUTF(env, (const char*)value);
    xmlFree(value);
    return jvalue;
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    getNsPropImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_Node_getNsPropImpl
(JNIEnv *env, jobject obj, jstring jName, jstring jHref) {
    xmlNode *node = findNode(env, obj);
    xmlChar *value;
    jstring jValue;
    
    const char *name = (*env)->GetStringUTFChars(env, jName, NULL);
    const char *href = (*env)->GetStringUTFChars(env, jHref, NULL);
    
    value = xmlGetNsProp(node, (const xmlChar*)name, (const xmlChar*)href);
    (*env)->NewStringUTF(env, (const char*)value);
    
    (*env)->ReleaseStringUTFChars(env, jName, name);
    (*env)->ReleaseStringUTFChars(env, jHref, href);
    
    jValue = (*env)->NewStringUTF(env, (const char*)value);
    xmlFree(value);
    return jValue;
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    setPropImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_setPropImpl
(JNIEnv *env, jobject obj, jstring jname, jstring jvalue) {
    xmlNode *node = findNode(env, obj);
    const char *name = (*env)->GetStringUTFChars(env, jname, NULL);
    const char *value = (*env)->GetStringUTFChars(env, jvalue, NULL);
    
    xmlNewProp(node, (xmlChar*)name, (xmlChar*)value);
    
    (*env)->ReleaseStringUTFChars(env, jname, name);
    (*env)->ReleaseStringUTFChars(env, jvalue, value);
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    removePropImpl
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_xmlsoft_Node_removePropImpl
(JNIEnv *env, jobject obj, jstring jname) {
    xmlNode *node = findNode(env, obj);
    const xmlChar *name = (xmlChar*)(*env)->GetStringUTFChars(env, jname, NULL);
    jboolean deleted = JNI_FALSE;
    
    xmlAttr *attr = node->properties;
    while(attr!=NULL) {
        if(xmlStrcmp(attr->name, name)==0) {
            xmlRemoveProp(attr);
            deleted = JNI_TRUE;
        }
        attr = attr->next;
    }
    (*env)->ReleaseStringUTFChars(env, jname, (const char*)name);
    
    return deleted;
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    addChildImpl
 * Signature: (Lrath/libxml/Node;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_addChildImpl
(JNIEnv *env, jobject obj, jobject toAdd) {
    xmlNode *node = findNode(env, obj);
    
    xmlNode *attached = xmlAddChild(node, findNode(env, toAdd));
    jobject jdoc = (*env)->GetObjectField(env, obj, fieldNodeDocument);
    jobject jattached = buildNode(env, attached, jdoc);
    (*env)->DeleteLocalRef(env, jdoc);
    return jattached;
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillAttributeNames
 * Signature: (Ljava/util/List;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillAttributeNames
  (JNIEnv *env, jobject obj, jobject listBuffer) {
    xmlNode *node = findNode(env, obj);

    xmlAttr *attr = NULL;
    for(attr=node->properties; attr!=NULL; attr=attr->next) {
        jstring name = (*env)->NewStringUTF(env, (const char*)attr->name);
        (*env)->CallBooleanMethod(env, listBuffer, methodListAdd, name);
    }
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    unlinkImpl
 * Signature: (Lrath/libxml/Node;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_unlinkImpl
(JNIEnv *env, jobject obj, jobject toRemove) {
    xmlNode *node = findNode(env, toRemove);
    xmlUnlinkNode(node);
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_disposeImpl
(JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    xmlFreeNode(node);
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    setTextImpl
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_setTextImpl
(JNIEnv *env, jobject obj, jstring jstr) {
    xmlNode *node = findNode(env, obj);
    const xmlChar *str = (const xmlChar*)(*env)->GetStringUTFChars(env, jstr, NULL);
    xmlNodeSetContent(node, str);
    (*env)->ReleaseStringUTFChars(env, jstr, (const char*)str);
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    addPrevSiblingImpl
 * Signature: (Lrath/libxml/Node;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_addPrevSiblingImpl
(JNIEnv *env, jobject obj, jobject toAdd) {
    xmlNode *node = findNode(env, obj);
    xmlNode *nodeToAdd = findNode(env, toAdd);
    jobject jdoc = (*env)->GetObjectField(env, obj, fieldNodeDocument);
    xmlNode *nodeAdded = xmlAddPrevSibling(node, nodeToAdd);
    jobject ret = buildNode(env, nodeAdded, jdoc);
    (*env)->DeleteLocalRef(env, jdoc);
    return ret;
}

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillAttributeNodes
 * Signature: (Ljava/util/List;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillAttributeNodes
(JNIEnv *env, jobject obj, jobject buf) {
    xmlNode *node = findNode(env, obj);
    xmlAttr *attr = node->properties;
    while(attr!=NULL) {
        jobject jattr;
        jobject jns = NULL;
        jobject jname = (*env)->NewStringUTF(env, (const char*)attr->name);
        jobject jvalue = (*env)->NewStringUTF(env, (const char*)attr->children->content);
        
        xmlNs *ns = attr->ns;
        if( ns!=NULL ) {
            jns = (*env)->NewObject(env, classNamespace, methodNamespaceNew,
                                    (*env)->NewStringUTF(env, (const char*)ns->href),
                                    (*env)->NewStringUTF(env, (const char*)ns->prefix));
        }
        jthrowable r = (*env)->ExceptionOccurred(env);
        
        jattr = (*env)->CallStaticObjectMethod(env, classAttribute, methodAttributeNew, jns, jname, jvalue);
        r = (*env)->ExceptionOccurred(env);
        (*env)->CallBooleanMethod(env, buf, methodListAdd, jattr);
        r = (*env)->ExceptionOccurred(env);
        
        (*env)->DeleteLocalRef(env, jns);
        (*env)->DeleteLocalRef(env, jname);
        (*env)->DeleteLocalRef(env, jvalue);
        (*env)->DeleteLocalRef(env, jattr);
        
        attr = attr->next;
    }
    
}
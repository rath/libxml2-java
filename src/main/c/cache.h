extern jclass classString;
extern jclass classError;
extern jclass classDocument;
extern jclass classNode;
extern jclass classNodeset;
extern jclass classNamespace;
extern jclass classXPathContext;
extern jclass classXPathObject;
extern jclass classLocator;
extern jclass classInputStream;
extern jclass classOutputStream;
extern jclass classAttribute;
extern jclass classXPathExpression;

extern jmethodID methodErrorNew;
extern jmethodID methodDocumentNew;
extern jmethodID methodNodeNew;
extern jmethodID methodNodeSetType;
extern jmethodID methodNodeSetDocument;
extern jmethodID methodNodesetNew;
extern jmethodID methodNodesetAddNode;
extern jmethodID methodNamespaceNew;
extern jmethodID methodXPathContextNew;
extern jmethodID methodXPathObjectNew;
extern jmethodID methodXPathExprNew;
extern jmethodID methodListAdd;
extern jmethodID methodLocatorNew;
extern jmethodID methodInputStreamRead;
extern jmethodID methodOutputStreamWrite;
extern jmethodID methodAttributeNew;

extern jfieldID fieldDocumentGetP;
extern jfieldID fieldNodeGetP;
extern jfieldID fieldNodeDocument;
extern jfieldID fieldXPathContextGetP;
extern jfieldID fieldXPathExprP;
extern jfieldID fieldNodeSetNamespace;
extern jfieldID fieldNodeSetName;
extern jfieldID fieldNodesetSize;

extern jfieldID fieldXPathContextDocument;
extern jfieldID fieldXPathObjectGetP;
extern jfieldID fieldXPathObjectSetNodeset;
extern jfieldID fieldXPathObjectSetBool;
extern jfieldID fieldXPathObjectSetFloat;
extern jfieldID fieldXPathObjectSetString;
extern jfieldID fieldLocatorP;
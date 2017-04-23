/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeInfo implements org.apache.thrift.TBase<NodeInfo, NodeInfo._Fields>, java.io.Serializable, Cloneable, Comparable<NodeInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("NodeInfo");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField HOST_FIELD_DESC = new org.apache.thrift.protocol.TField("host", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PORT_FIELD_DESC = new org.apache.thrift.protocol.TField("port", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField SUCC_HOST_FIELD_DESC = new org.apache.thrift.protocol.TField("succHost", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField SUCC_PORT_FIELD_DESC = new org.apache.thrift.protocol.TField("succPort", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField PRED_HOST_FIELD_DESC = new org.apache.thrift.protocol.TField("predHost", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField PRED_PORT_FIELD_DESC = new org.apache.thrift.protocol.TField("predPort", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new NodeInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new NodeInfoTupleSchemeFactory());
  }

  public int id; // required
  public String host; // required
  public int port; // required
  public String succHost; // required
  public int succPort; // required
  public String predHost; // required
  public int predPort; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    HOST((short)2, "host"),
    PORT((short)3, "port"),
    SUCC_HOST((short)4, "succHost"),
    SUCC_PORT((short)5, "succPort"),
    PRED_HOST((short)6, "predHost"),
    PRED_PORT((short)7, "predPort");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // HOST
          return HOST;
        case 3: // PORT
          return PORT;
        case 4: // SUCC_HOST
          return SUCC_HOST;
        case 5: // SUCC_PORT
          return SUCC_PORT;
        case 6: // PRED_HOST
          return PRED_HOST;
        case 7: // PRED_PORT
          return PRED_PORT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __PORT_ISSET_ID = 1;
  private static final int __SUCCPORT_ISSET_ID = 2;
  private static final int __PREDPORT_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.HOST, new org.apache.thrift.meta_data.FieldMetaData("host", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PORT, new org.apache.thrift.meta_data.FieldMetaData("port", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SUCC_HOST, new org.apache.thrift.meta_data.FieldMetaData("succHost", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUCC_PORT, new org.apache.thrift.meta_data.FieldMetaData("succPort", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRED_HOST, new org.apache.thrift.meta_data.FieldMetaData("predHost", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRED_PORT, new org.apache.thrift.meta_data.FieldMetaData("predPort", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(NodeInfo.class, metaDataMap);
  }

  public NodeInfo() {
  }

  public NodeInfo(
    int id,
    String host,
    int port,
    String succHost,
    int succPort,
    String predHost,
    int predPort)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.host = host;
    this.port = port;
    setPortIsSet(true);
    this.succHost = succHost;
    this.succPort = succPort;
    setSuccPortIsSet(true);
    this.predHost = predHost;
    this.predPort = predPort;
    setPredPortIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public NodeInfo(NodeInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetHost()) {
      this.host = other.host;
    }
    this.port = other.port;
    if (other.isSetSuccHost()) {
      this.succHost = other.succHost;
    }
    this.succPort = other.succPort;
    if (other.isSetPredHost()) {
      this.predHost = other.predHost;
    }
    this.predPort = other.predPort;
  }

  public NodeInfo deepCopy() {
    return new NodeInfo(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.host = null;
    setPortIsSet(false);
    this.port = 0;
    this.succHost = null;
    setSuccPortIsSet(false);
    this.succPort = 0;
    this.predHost = null;
    setPredPortIsSet(false);
    this.predPort = 0;
  }

  public int getId() {
    return this.id;
  }

  public NodeInfo setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public String getHost() {
    return this.host;
  }

  public NodeInfo setHost(String host) {
    this.host = host;
    return this;
  }

  public void unsetHost() {
    this.host = null;
  }

  /** Returns true if field host is set (has been assigned a value) and false otherwise */
  public boolean isSetHost() {
    return this.host != null;
  }

  public void setHostIsSet(boolean value) {
    if (!value) {
      this.host = null;
    }
  }

  public int getPort() {
    return this.port;
  }

  public NodeInfo setPort(int port) {
    this.port = port;
    setPortIsSet(true);
    return this;
  }

  public void unsetPort() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  /** Returns true if field port is set (has been assigned a value) and false otherwise */
  public boolean isSetPort() {
    return EncodingUtils.testBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  public void setPortIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PORT_ISSET_ID, value);
  }

  public String getSuccHost() {
    return this.succHost;
  }

  public NodeInfo setSuccHost(String succHost) {
    this.succHost = succHost;
    return this;
  }

  public void unsetSuccHost() {
    this.succHost = null;
  }

  /** Returns true if field succHost is set (has been assigned a value) and false otherwise */
  public boolean isSetSuccHost() {
    return this.succHost != null;
  }

  public void setSuccHostIsSet(boolean value) {
    if (!value) {
      this.succHost = null;
    }
  }

  public int getSuccPort() {
    return this.succPort;
  }

  public NodeInfo setSuccPort(int succPort) {
    this.succPort = succPort;
    setSuccPortIsSet(true);
    return this;
  }

  public void unsetSuccPort() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SUCCPORT_ISSET_ID);
  }

  /** Returns true if field succPort is set (has been assigned a value) and false otherwise */
  public boolean isSetSuccPort() {
    return EncodingUtils.testBit(__isset_bitfield, __SUCCPORT_ISSET_ID);
  }

  public void setSuccPortIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SUCCPORT_ISSET_ID, value);
  }

  public String getPredHost() {
    return this.predHost;
  }

  public NodeInfo setPredHost(String predHost) {
    this.predHost = predHost;
    return this;
  }

  public void unsetPredHost() {
    this.predHost = null;
  }

  /** Returns true if field predHost is set (has been assigned a value) and false otherwise */
  public boolean isSetPredHost() {
    return this.predHost != null;
  }

  public void setPredHostIsSet(boolean value) {
    if (!value) {
      this.predHost = null;
    }
  }

  public int getPredPort() {
    return this.predPort;
  }

  public NodeInfo setPredPort(int predPort) {
    this.predPort = predPort;
    setPredPortIsSet(true);
    return this;
  }

  public void unsetPredPort() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PREDPORT_ISSET_ID);
  }

  /** Returns true if field predPort is set (has been assigned a value) and false otherwise */
  public boolean isSetPredPort() {
    return EncodingUtils.testBit(__isset_bitfield, __PREDPORT_ISSET_ID);
  }

  public void setPredPortIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PREDPORT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case HOST:
      if (value == null) {
        unsetHost();
      } else {
        setHost((String)value);
      }
      break;

    case PORT:
      if (value == null) {
        unsetPort();
      } else {
        setPort((Integer)value);
      }
      break;

    case SUCC_HOST:
      if (value == null) {
        unsetSuccHost();
      } else {
        setSuccHost((String)value);
      }
      break;

    case SUCC_PORT:
      if (value == null) {
        unsetSuccPort();
      } else {
        setSuccPort((Integer)value);
      }
      break;

    case PRED_HOST:
      if (value == null) {
        unsetPredHost();
      } else {
        setPredHost((String)value);
      }
      break;

    case PRED_PORT:
      if (value == null) {
        unsetPredPort();
      } else {
        setPredPort((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case HOST:
      return getHost();

    case PORT:
      return Integer.valueOf(getPort());

    case SUCC_HOST:
      return getSuccHost();

    case SUCC_PORT:
      return Integer.valueOf(getSuccPort());

    case PRED_HOST:
      return getPredHost();

    case PRED_PORT:
      return Integer.valueOf(getPredPort());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case HOST:
      return isSetHost();
    case PORT:
      return isSetPort();
    case SUCC_HOST:
      return isSetSuccHost();
    case SUCC_PORT:
      return isSetSuccPort();
    case PRED_HOST:
      return isSetPredHost();
    case PRED_PORT:
      return isSetPredPort();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof NodeInfo)
      return this.equals((NodeInfo)that);
    return false;
  }

  public boolean equals(NodeInfo that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_host = true && this.isSetHost();
    boolean that_present_host = true && that.isSetHost();
    if (this_present_host || that_present_host) {
      if (!(this_present_host && that_present_host))
        return false;
      if (!this.host.equals(that.host))
        return false;
    }

    boolean this_present_port = true;
    boolean that_present_port = true;
    if (this_present_port || that_present_port) {
      if (!(this_present_port && that_present_port))
        return false;
      if (this.port != that.port)
        return false;
    }

    boolean this_present_succHost = true && this.isSetSuccHost();
    boolean that_present_succHost = true && that.isSetSuccHost();
    if (this_present_succHost || that_present_succHost) {
      if (!(this_present_succHost && that_present_succHost))
        return false;
      if (!this.succHost.equals(that.succHost))
        return false;
    }

    boolean this_present_succPort = true;
    boolean that_present_succPort = true;
    if (this_present_succPort || that_present_succPort) {
      if (!(this_present_succPort && that_present_succPort))
        return false;
      if (this.succPort != that.succPort)
        return false;
    }

    boolean this_present_predHost = true && this.isSetPredHost();
    boolean that_present_predHost = true && that.isSetPredHost();
    if (this_present_predHost || that_present_predHost) {
      if (!(this_present_predHost && that_present_predHost))
        return false;
      if (!this.predHost.equals(that.predHost))
        return false;
    }

    boolean this_present_predPort = true;
    boolean that_present_predPort = true;
    if (this_present_predPort || that_present_predPort) {
      if (!(this_present_predPort && that_present_predPort))
        return false;
      if (this.predPort != that.predPort)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(NodeInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHost()).compareTo(other.isSetHost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.host, other.host);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPort()).compareTo(other.isSetPort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.port, other.port);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSuccHost()).compareTo(other.isSetSuccHost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuccHost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.succHost, other.succHost);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSuccPort()).compareTo(other.isSetSuccPort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuccPort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.succPort, other.succPort);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPredHost()).compareTo(other.isSetPredHost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPredHost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.predHost, other.predHost);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPredPort()).compareTo(other.isSetPredPort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPredPort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.predPort, other.predPort);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("NodeInfo(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("host:");
    if (this.host == null) {
      sb.append("null");
    } else {
      sb.append(this.host);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("port:");
    sb.append(this.port);
    first = false;
    if (!first) sb.append(", ");
    sb.append("succHost:");
    if (this.succHost == null) {
      sb.append("null");
    } else {
      sb.append(this.succHost);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("succPort:");
    sb.append(this.succPort);
    first = false;
    if (!first) sb.append(", ");
    sb.append("predHost:");
    if (this.predHost == null) {
      sb.append("null");
    } else {
      sb.append(this.predHost);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("predPort:");
    sb.append(this.predPort);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'id' because it's a primitive and you chose the non-beans generator.
    if (host == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'host' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'port' because it's a primitive and you chose the non-beans generator.
    if (succHost == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'succHost' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'succPort' because it's a primitive and you chose the non-beans generator.
    if (predHost == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'predHost' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'predPort' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class NodeInfoStandardSchemeFactory implements SchemeFactory {
    public NodeInfoStandardScheme getScheme() {
      return new NodeInfoStandardScheme();
    }
  }

  private static class NodeInfoStandardScheme extends StandardScheme<NodeInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, NodeInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HOST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.host = iprot.readString();
              struct.setHostIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PORT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.port = iprot.readI32();
              struct.setPortIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SUCC_HOST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.succHost = iprot.readString();
              struct.setSuccHostIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SUCC_PORT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.succPort = iprot.readI32();
              struct.setSuccPortIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PRED_HOST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.predHost = iprot.readString();
              struct.setPredHostIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // PRED_PORT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.predPort = iprot.readI32();
              struct.setPredPortIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'id' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetPort()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'port' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetSuccPort()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'succPort' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetPredPort()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'predPort' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, NodeInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      if (struct.host != null) {
        oprot.writeFieldBegin(HOST_FIELD_DESC);
        oprot.writeString(struct.host);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PORT_FIELD_DESC);
      oprot.writeI32(struct.port);
      oprot.writeFieldEnd();
      if (struct.succHost != null) {
        oprot.writeFieldBegin(SUCC_HOST_FIELD_DESC);
        oprot.writeString(struct.succHost);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SUCC_PORT_FIELD_DESC);
      oprot.writeI32(struct.succPort);
      oprot.writeFieldEnd();
      if (struct.predHost != null) {
        oprot.writeFieldBegin(PRED_HOST_FIELD_DESC);
        oprot.writeString(struct.predHost);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PRED_PORT_FIELD_DESC);
      oprot.writeI32(struct.predPort);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class NodeInfoTupleSchemeFactory implements SchemeFactory {
    public NodeInfoTupleScheme getScheme() {
      return new NodeInfoTupleScheme();
    }
  }

  private static class NodeInfoTupleScheme extends TupleScheme<NodeInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, NodeInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.id);
      oprot.writeString(struct.host);
      oprot.writeI32(struct.port);
      oprot.writeString(struct.succHost);
      oprot.writeI32(struct.succPort);
      oprot.writeString(struct.predHost);
      oprot.writeI32(struct.predPort);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, NodeInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.id = iprot.readI32();
      struct.setIdIsSet(true);
      struct.host = iprot.readString();
      struct.setHostIsSet(true);
      struct.port = iprot.readI32();
      struct.setPortIsSet(true);
      struct.succHost = iprot.readString();
      struct.setSuccHostIsSet(true);
      struct.succPort = iprot.readI32();
      struct.setSuccPortIsSet(true);
      struct.predHost = iprot.readString();
      struct.setPredHostIsSet(true);
      struct.predPort = iprot.readI32();
      struct.setPredPortIsSet(true);
    }
  }

}


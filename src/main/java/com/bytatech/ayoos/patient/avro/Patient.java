/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.bytatech.ayoos.patient.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Patient extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -9122485174436532761L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Patient\",\"namespace\":\"com.bytatech.ayoos.patient.avro\",\"fields\":[{\"name\":\"name\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Patient> ENCODER =
      new BinaryMessageEncoder<Patient>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Patient> DECODER =
      new BinaryMessageDecoder<Patient>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Patient> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Patient> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Patient>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Patient to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Patient from a ByteBuffer. */
  public static Patient fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String name;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Patient() {}

  /**
   * All-args constructor.
   * @param name The new value for name
   */
  public Patient(java.lang.String name) {
    this.name = name;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return name;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: name = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.String value) {
    this.name = value;
  }

  /**
   * Creates a new Patient RecordBuilder.
   * @return A new Patient RecordBuilder
   */
  public static com.bytatech.ayoos.patient.avro.Patient.Builder newBuilder() {
    return new com.bytatech.ayoos.patient.avro.Patient.Builder();
  }

  /**
   * Creates a new Patient RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Patient RecordBuilder
   */
  public static com.bytatech.ayoos.patient.avro.Patient.Builder newBuilder(com.bytatech.ayoos.patient.avro.Patient.Builder other) {
    return new com.bytatech.ayoos.patient.avro.Patient.Builder(other);
  }

  /**
   * Creates a new Patient RecordBuilder by copying an existing Patient instance.
   * @param other The existing instance to copy.
   * @return A new Patient RecordBuilder
   */
  public static com.bytatech.ayoos.patient.avro.Patient.Builder newBuilder(com.bytatech.ayoos.patient.avro.Patient other) {
    return new com.bytatech.ayoos.patient.avro.Patient.Builder(other);
  }

  /**
   * RecordBuilder for Patient instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Patient>
    implements org.apache.avro.data.RecordBuilder<Patient> {

    private java.lang.String name;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.bytatech.ayoos.patient.avro.Patient.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Patient instance
     * @param other The existing instance to copy.
     */
    private Builder(com.bytatech.ayoos.patient.avro.Patient other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.String getName() {
      return name;
    }

    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public com.bytatech.ayoos.patient.avro.Patient.Builder setName(java.lang.String value) {
      validate(fields()[0], value);
      this.name = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public com.bytatech.ayoos.patient.avro.Patient.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Patient build() {
      try {
        Patient record = new Patient();
        record.name = fieldSetFlags()[0] ? this.name : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Patient>
    WRITER$ = (org.apache.avro.io.DatumWriter<Patient>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Patient>
    READER$ = (org.apache.avro.io.DatumReader<Patient>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}

package org.sydwildlife.api.persistence.converter;

import java.util.UUID;

import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.sessions.Session;

public class UUIDConverter implements org.eclipse.persistence.mappings.converters.Converter {

   private static final long serialVersionUID = 1L;

   @Override
   public Object convertObjectValueToDataValue(Object objectValue,
         Session session) {
      return objectValue.toString();

   }

   @Override
   public UUID convertDataValueToObjectValue(Object dataValue,
         Session session) {
      if (dataValue instanceof UUID) {
         return (UUID) dataValue;
      } else if (dataValue instanceof String) {
         return UUID.fromString((String) dataValue);
      } else {
         throw new RuntimeException("cannot convert " + dataValue.getClass().getName() + " to UUID");
      }
   }

   @Override
   public boolean isMutable() {
      return true;
   }

   public UUIDConverter() {
      super();
      // TODO Auto-generated constructor stub
   }

   @Override
   public void initialize(DatabaseMapping mapping, Session session) {
      final DatabaseField field;
      if (mapping instanceof DirectCollectionMapping) {
         // handle @ElementCollection...
         field = ((DirectCollectionMapping) mapping).getDirectField();
      } else {
         field = mapping.getField();
      }

      field.setSqlType(java.sql.Types.OTHER);
      // field.setTypeName("uuid");
      field.setColumnDefinition("UUID");
   }
}

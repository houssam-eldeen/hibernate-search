/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.search.elasticsearch.nulls.impl;

import org.hibernate.search.bridge.spi.NullMarker;
import org.hibernate.search.elasticsearch.logging.impl.Log;
import org.hibernate.search.elasticsearch.nulls.codec.impl.ElasticsearchAsNullStringNullMarkerCodec;
import org.hibernate.search.elasticsearch.nulls.codec.impl.ElasticsearchBooleanNullMarkerCodec;
import org.hibernate.search.elasticsearch.nulls.codec.impl.ElasticsearchDoubleNullMarkerCodec;
import org.hibernate.search.elasticsearch.nulls.codec.impl.ElasticsearchFloatNullMarkerCodec;
import org.hibernate.search.elasticsearch.nulls.codec.impl.ElasticsearchIntegerNullMarkerCodec;
import org.hibernate.search.elasticsearch.nulls.codec.impl.ElasticsearchLongNullMarkerCodec;
import org.hibernate.search.engine.metadata.impl.PartialDocumentFieldMetadata;
import org.hibernate.search.engine.nulls.codec.impl.NullMarkerCodec;
import org.hibernate.search.engine.nulls.impl.MissingValueStrategy;
import org.hibernate.search.util.logging.impl.LoggerFactory;

public final class Elasticsearch2MissingValueStrategy implements MissingValueStrategy {
	private static final Log LOG = LoggerFactory.make( Log.class );

	public static final Elasticsearch2MissingValueStrategy INSTANCE = new Elasticsearch2MissingValueStrategy();

	@Override
	public NullMarkerCodec createNullMarkerCodec(Class<?> entityType,
			PartialDocumentFieldMetadata fieldMetadata, NullMarker nullMarker) {
		Object nullEncoded = nullMarker.nullEncoded();
		if ( nullEncoded instanceof String ) {
			return new ElasticsearchAsNullStringNullMarkerCodec( nullMarker );
		}
		else if ( nullEncoded instanceof Integer ) {
			return new ElasticsearchIntegerNullMarkerCodec( nullMarker );
		}
		else if ( nullEncoded instanceof Long ) {
			return new ElasticsearchLongNullMarkerCodec( nullMarker );
		}
		else if ( nullEncoded instanceof Float ) {
			return new ElasticsearchFloatNullMarkerCodec( nullMarker );
		}
		else if ( nullEncoded instanceof Double ) {
			return new ElasticsearchDoubleNullMarkerCodec( nullMarker );
		}
		else if ( nullEncoded instanceof Boolean ) {
			return new ElasticsearchBooleanNullMarkerCodec( nullMarker );
		}
		else {
			throw LOG.unsupportedNullTokenType( entityType, fieldMetadata.getPath().getAbsoluteName(),
					nullEncoded.getClass() );
		}
	}
}
package org.springframework.datastore.graph.neo4j.fieldaccess;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.datastore.graph.api.NodeBacked;
import org.springframework.persistence.support.EntityInstantiator;

import java.util.Collections;
import java.util.Set;

public class SingleRelationshipFieldAccessor extends AbstractFieldAccessor {
    public SingleRelationshipFieldAccessor(final RelationshipType type, final Direction direction, final Class<? extends NodeBacked> clazz, final EntityInstantiator<NodeBacked, Node> graphEntityInstantiator) {
        super(clazz, graphEntityInstantiator, direction, type);
    }

	@Override
    public Object setValue(final NodeBacked entity, final Object newVal) {
        checkUnderlyingNode(entity);
        if (newVal == null) {
            removeMissingRelationships(entity, Collections.<NodeBacked>emptySet());
            return null;
        }
        final Set<NodeBacked> target=checkTargetIsSetOfNodebacked(Collections.singleton(newVal));
        checkNoCircularReference(entity,target);
        removeMissingRelationships(entity, target);
		createNewRelationshipsFrom(entity,target);
        return newVal;
	}

    @Override
	public Object getValue(final NodeBacked entity) {
        checkUnderlyingNode(entity);
        final Set<NodeBacked> result = createEntitySetFromRelationshipEndNodes(entity);
        return result.isEmpty() ? null : result.iterator().next();
	}
}
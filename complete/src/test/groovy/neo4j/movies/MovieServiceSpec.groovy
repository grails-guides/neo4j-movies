package neo4j.movies

import grails.test.neo4j.Neo4jSpec
import org.grails.datastore.gorm.neo4j.Neo4jDatastore

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@SuppressWarnings(['MethodName', 'Instanceof'])
class MovieServiceSpec extends Neo4jSpec {

    MovieService service

    void setup() {
        Neo4jDatastore neo4jDatastore = this.neo4jDatastore
        service = neo4jDatastore.services.find { it instanceof MovieService }
    }

    void 'Test search movies'() {
        expect: 'to find results'
        service.search('Matr')
        service.search('The')
        !service.search('The Wrestler')
    }

    void 'Test find movie by title'() {
        expect: 'The find movies by title'
        service.find('The Matrix')
        !service.find('The Wrestler')
    }
}

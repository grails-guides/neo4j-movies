package neo4j.movies

import grails.gorm.transactions.Rollback
import grails.test.neo4j.Neo4jSpec
import grails.testing.spock.OnceBefore
import spock.lang.Shared

@SuppressWarnings(['MethodName', 'DuplicateNumberLiteral', 'UnnecessaryGetter', 'TrailingWhitespace'])
@IgnoreIf( { System.getenv('TRAVIS') as boolean } )
class MovieServiceSpec extends Neo4jSpec {

    protected List<Class> getDomainClasses() { [Movie] }

    @Shared MovieService service

    @OnceBefore
    def serviceSetup() {
        service = neo4jDatastore.getService(MovieService)
    }

    @Rollback
    void 'Test search movies'() {
        given:
        new Movie(title: 'The Matrix', tagline: 'Free your mind', released: 1999).save(flush:true)

        expect: 'to find results'
        service.search('Matr')
        service.search('The')
        !service.search('The Wrestler')
    }

    @Rollback
    void 'Test find movie by title'() {
        given:
        new Movie(title: 'The Matrix', tagline: 'Free your mind', released: 1999).save(flush:true)

        expect: 'The find movies by title'
        service.find('The Matrix')
        !service.find('The Wrestler')
    }
}

package neo4j.movies

import grails.gorm.transactions.Rollback
import grails.test.neo4j.Neo4jSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Ignore

@Ignore
@SuppressWarnings(['MethodName', 'DuplicateNumberLiteral', 'UnnecessaryGetter', 'TrailingWhitespace'])
class MovieServiceSpec extends Neo4jSpec implements ServiceUnitTest<MovieService>  {

    protected List<Class> getDomainClasses() { [Movie] }

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

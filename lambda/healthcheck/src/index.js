
import fetch from 'node-fetch' ;

const site = process.env.SITE ?? 'https://www.vkecanada.com/healthcheck.html' ;
const expected = process.env.EXPECTED ?? 'Response: 200 ok' ;

const handler =  async function(event, context) {
    try {
        const response = await fetch(site) ;
        const body = await response.text() ;
        if(body !== expected) {
            return {
                statusCode: 403,
                body: JSON.stringify('check failed')
            }
        }
        else {
            return {
                statusCode: 200,
                body: 'check passed'
            }
        }
    }
    catch(error) {
        return {
            statusCode: 500,
            body: JSON.stringify(error, null, 2)
        }
    }
} 

export {handler}
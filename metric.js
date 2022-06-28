const fs = require('fs')
const prs = require('node-html-parser')

/*---------------*CONFIGURATION*---------------*/

const doesMakePostRequest = false

const reportFile = 'build/reports/kover/project-html/index.html'
const testFolder = 'src/test/kotlin/com/ua/libraryRESTful'

const lineMetricDataIndex = 8
const instructionDataIndex = 9

/*---------------------------------------------*/

const htmlCoverReport = fs.readFileSync(reportFile, 'UTF-8')
const reportDocument = prs.parse(htmlCoverReport)

let lineContent = pullLineContent(reportDocument)
let instructionContent = pullInstructionContent(reportDocument)
let totalTestLines = recursiveResult(testFolder)

console.log('Line content: ', lineContent)
console.log('Instruction content: ', instructionContent)
console.log('Total lines: ', totalTestLines)

const response = sendToStatisticServer()
console.log(response)

function pullLineContent(reportDocument) {
    return reportDocument
        .querySelectorAll('.coverageStat')[lineMetricDataIndex]
        .textContent.replace(/[\s{()}]/g, '')
        .split('/')[1]
}

function pullInstructionContent(reportDocument) {
    return reportDocument
        .querySelectorAll('.coverageStat')[instructionDataIndex]
        .textContent.replace(/\s/g, '')
        .split('%')[0]
}

function recursiveResult(dirPath, temp = 0) {
    const files = fs.readdirSync(dirPath)
    files.forEach(file => {

        const pathToFile = dirPath + '/' + file
        const stat = fs.lstatSync(pathToFile)

        if (stat.isFile()) {
            temp += countLinesInFile(pathToFile)
        } else if (stat.isDirectory()) {
            temp = recursiveResult(pathToFile, temp)
        }

    });
    return temp
}

function countLinesInFile(file) {
    const text = fs.readFileSync(file, 'UTF-8')
    return text.split('\n').length
}

function sendToStatisticServer() {
    if (!doesMakePostRequest)
        return 'Saving operation to statistic server is disabled'

    const data = jsonData()
    fetch('', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Operation success!')
            return data
        })
        .catch(err => {
            console.log("Error!")
            return err
        })
}

function jsonData() {
    return {
        'lineContent': lineContent,
        'instructionContent': instructionContent,
        'totalTestsLines': totalTestLines
    }
}
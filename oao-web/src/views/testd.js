var fs = require('fs');
var result = JSON.parse(fs.readFileSync('./department.json'));

let temp = result.data.filter(o => {
  let subCode = o.code.substring(0, o.code.length - 2)
  let findFlag = result.data.find(parent => {
    let pCode = parent.code
    let flag = (subCode == pCode)
    if (flag) {
      // console.log(flag)
      // console.log('parent:' + pCode)
      // console.log('child:' + subCode)
    }
    return flag
  })
  // console.log(!!findFlag)
  return !findFlag
})

console.log(temp)
console.log(temp.length)

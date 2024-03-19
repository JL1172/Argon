import fs from "fs";
import { exec } from "child_process";

const sourceCodeFilePath = "./source-code.ar";

fs.readFile(sourceCodeFilePath, "utf-8", (err, data) => {
  if (err) {
    console.error(err);
  } else {
    process.chdir("../");
    console.log(process.cwd());
    exec(`java Main.java ${"jacob"}`, (error, stdout, stderr) => {
      if (error) {
        console.error("Error Compiling Java File.", error);
        process.exit(1);
      } else {
        console.log("stdout: ", stdout);
        console.log("stderr: ", stderr);
      }
    });
  }
});

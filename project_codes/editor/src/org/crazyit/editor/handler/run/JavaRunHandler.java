package org.crazyit.editor.handler.run;

import java.io.File;

import org.crazyit.editor.EditorFrame;
import org.crazyit.editor.config.CompileConfig;
import org.crazyit.editor.util.CommandUtil;

/**
 * 运行Java文件处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class JavaRunHandler {

	public String run(EditorFrame editorFrame) {
		try {
			//获得项目目录的路径
			String projectPath = editorFrame.getCurrentProject().getAbsolutePath();
			//获得源文件的全路径
			String sourcePath = editorFrame.getCurrentFile().getFile().getAbsolutePath();
			//获得项目的编译路径，项目目录加CompileConfig中配置的输出目录
			String classPath = editorFrame.getCurrentProject().getAbsolutePath() 
				+ File.separator + CompileConfig.OUTPUT_DIR;
			//获取类名
			String className = getClassName(projectPath, sourcePath);
			//拼装命令
			String command = "java -cp \"" + classPath + "\" " + className;
			Process p = CommandUtil.executeCommand(command);
			return CommandUtil.getRunString(p);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	//根据项目目录的路径和Java源文件的路径获取一个类的全限定类名
	private String getClassName(String projectPath, String sourcePath) {
		String temp = projectPath + File.separator + CompileConfig.SRC_DIR + 
		File.separator;
		String result = sourcePath.replace(temp, "");
		result = result.replace(".java", "");
		result = result.replace(String.valueOf(File.separatorChar), ".");
		return result;
	}
	

}

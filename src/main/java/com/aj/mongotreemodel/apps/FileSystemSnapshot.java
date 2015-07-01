package com.aj.mongotreemodel.apps;

import java.io.File;
import java.util.Date;

import com.aj.mongotreemodel.IMongoTreeModel;
import com.aj.mongotreemodel.Node;
import com.aj.mongotreemodel.excpn.MongoTreeModelException;
import com.aj.mongotreemodel.impl.ParentReferenceImpl;

public class FileSystemSnapshot {

	IMongoTreeModel<String, MyFile> tree = null;

	public FileSystemSnapshot(String root) throws MongoTreeModelException {
		tree = new ParentReferenceImpl<String, MyFile>();
		tree.createRoot(new Node<String, MyFile>(root, null,null));
		traverse(new File(root));
	}

	private void traverse(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; children != null && i < children.length; i++) {
				File fl = new File(dir, children[i]);
				tree.attachChild(new Node<String, MyFile>(fl.getAbsolutePath(),
						fl.getParent(), new MyFile(fl)));
				traverse(new File(dir, children[i]));
			}
		}
	}
	public static void main(String[] args) throws MongoTreeModelException {
		if(args.length > 0)
			new FileSystemSnapshot(args[0]);
		else
			new FileSystemSnapshot("/Users/ajay_edapalapaty/Test");
	}
	
	class MyFile{
		String type;
		Date lastModified;
		Long size;
		Integer count;
		public MyFile(File file){
			if(!file.isDirectory()){
				type = "File";
				lastModified = new Date(file.lastModified());
				size = file.length();
			} else {
				type = "Dir";
				count = file.list().length;
			}
		}
	}
}


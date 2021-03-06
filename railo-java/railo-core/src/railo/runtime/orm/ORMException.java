package railo.runtime.orm;


import railo.runtime.Component;
import railo.runtime.db.DataSource;
import railo.runtime.exp.ApplicationException;
import railo.runtime.exp.PageException;
import railo.runtime.exp.PageExceptionImpl;
import railo.runtime.op.Caster;
import railo.runtime.type.List;
import railo.runtime.type.util.KeyConstants;

public class ORMException extends ApplicationException {

	/**
	 * Constructor of the class
	 * @param message
	 */
	public ORMException(Component cfc,String message) {
		super(message);
		setContext(cfc);
	}

	public ORMException(ORMEngine engine,Component cfc,String message) {
		super(message);
		setAddional(engine,this);
		setContext(cfc);
	}

	public ORMException(ORMEngine engine,String message) {
		super(message);
		setAddional(engine,this);
	}

	/**
	 * Constructor of the class
	 * @param message
	 * @param detail
	 */
	public ORMException(Component cfc,String message, String detail) {
		super(message, detail);
		setContext(cfc);
	}
	public ORMException(String message, String detail) {
		super(message, detail);
	}
	

	public ORMException(ORMEngine engine,Component cfc,String message, String detail) {
		super(message, detail);
		setAddional(engine, this);
		setContext(cfc);
	}
	

	public ORMException(ORMEngine engine,String message, String detail) {
		super(message, detail);
		setAddional(engine, this);
	}
	

	/*public ORMException(String message) {
		super(message);
	}*/
	

	private void setContext(Component cfc) {
		if(cfc!=null && getPageDeep()==0)addContext(cfc.getPageSource(), 1, 1, null);
	}

	public static PageException toPageException(ORMEngine engine,Throwable t) {
		Throwable c = t.getCause();
		if(c!=null)t=c;
		PageException pe = Caster.toPageException(t);
		setAddional(engine, pe);
		return pe;
	}
	
	public static void setAddional(ORMEngine engine,PageException pe) {
		String[] names = engine.getEntityNames();
		
		if(pe instanceof PageExceptionImpl){
			PageExceptionImpl pei = (PageExceptionImpl)pe;
			String dsn=null;
			DataSource ds = engine.getDataSource();
			if(ds!=null) dsn=ds.getName();
			pei.setAdditional(KeyConstants._Entities, List.arrayToList(names, ", "));
			if(dsn!=null)pei.setAdditional(KeyConstants._Datasource, dsn);
		}
	}

	public ORMException(Throwable t) {
		super(t.getMessage());
	}

}

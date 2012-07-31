@XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
package ch.boxi.togetherLess.businessLogic.dto;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ch.boxi.togetherLess.businessLogic.dto.XmlAdapter.DateAdapter;
import java.util.Date;

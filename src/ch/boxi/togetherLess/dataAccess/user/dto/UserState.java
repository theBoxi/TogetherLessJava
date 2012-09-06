package ch.boxi.togetherLess.dataAccess.user.dto;

import javax.persistence.Inheritance;

 /*
  *   ____________                      __________             ____________
  *  |            |   validate email   |          |  cancel   |            |
  *  | registered |------------------->|  active  |---------->|  canceled  |
  *  |____________|                    |__________|           |____________|
  *    
  */

@Inheritance
public enum UserState {
	registered,
	active,
	canceled;
}

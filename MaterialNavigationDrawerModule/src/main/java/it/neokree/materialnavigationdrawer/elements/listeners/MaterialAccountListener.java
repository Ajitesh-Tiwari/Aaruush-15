package it.neokree.materialnavigationdrawer.elements.listeners;

import it.neokree.materialnavigationdrawer.elements.MaterialAccount;

/**
 * Created by neokree on 11/12/14.
 */
public interface MaterialAccountListener {

    void onAccountOpening(MaterialAccount account);

    void onChangeAccount(MaterialAccount newAccount);

}
